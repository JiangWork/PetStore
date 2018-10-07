package com.jiangwork.action.petstore.metrics;

import com.codahale.metrics.*;
import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jiang on 2018/10/7.
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class MetricsAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetricsAspect.class);
    private final static Map<ProceedingJoinPointHolder, MetricedInfo> METRICED_INFO_CACHE = Maps.newConcurrentMap();

    @Autowired
    private MetricRegistry metricRegistry;

    @Around("@annotation(com.jiangwork.action.petstore.metrics.Metriced)" +
            "|| @annotation(com.jiangwork.action.petstore.metrics.Metrics)")
    public Object advice(ProceedingJoinPoint pjp) throws Throwable {
        MetricedInfo metricsInfo = getMetricsInfo(pjp);
        String method = metricsInfo.annotatedMethd.getDeclaringClass().getName() + "." + metricsInfo.annotatedMethd.getName();
        beforeInvocation(metricsInfo.metriceds, method);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            LOGGER.error("Failed to executing {}: {}", method, Throwables.getStackTraceAsString(e));
            throw e;
        } finally {
            afterInvocation(metricsInfo.metriceds, method, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }

    private void beforeInvocation(Set<Metriced> metriceds, String method) {
        Counter methodExecutingCounter = metricRegistry.counter(method + ".executing-internal");
        methodExecutingCounter.inc();
        for (Metriced metriced : metriceds) {
            Class<?> clazz = metriced.clazz();
            String name = metriced.name();
            String metricName = method + "." + name;
            if (Meter.class == clazz) {
                Meter meter = metricRegistry.meter(metricName);
                meter.mark();
            } else if (Gauge.class == clazz) {
                metricRegistry.gauge(metricName, () -> methodExecutingCounter::getCount);
            } else if (Counter.class == clazz) {
                Counter counter = metricRegistry.counter(metricName);
                counter.inc();
            } else if (Histogram.class == clazz) {
                // skip
            } else {
                LOGGER.error("Unsupported metric type for beforeInvocation {}", clazz);
            }
        }
    }

    private void afterInvocation(Set<Metriced> metriceds, String method, long elapsedTime) {
        Counter methodExecutingCounter = metricRegistry.counter(method + ".executing-internal");
        methodExecutingCounter.dec();
        for (Metriced metriced : metriceds) {
            Class<?> clazz = metriced.clazz();
            if (Histogram.class == clazz) {
                String name = metriced.name();
                String metricName = method + "." + name;
                metricRegistry.histogram(metricName).update(elapsedTime);
            }
        }
    }

    @PostConstruct
    private void init() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    private MetricedInfo getMetricsInfo(ProceedingJoinPoint pjp) {
        return METRICED_INFO_CACHE.computeIfAbsent(new ProceedingJoinPointHolder(pjp), key -> {
            LOGGER.info("Obtaining metrics information {}", pjp);
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Class<?> objClazz = pjp.getTarget().getClass();
            Method currentMethod = null;
            try {
                currentMethod = objClazz.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
                Set<Metriced> metricedSet = AnnotatedElementUtils.getMergedRepeatableAnnotations(currentMethod, Metriced.class, Metrics.class);
                return MetricedInfo.of(metricedSet, currentMethod);
            } catch (NoSuchMethodException e) {
                LOGGER.error("Cannot find method annotated with Metrics {}", methodSignature);
            }
            return MetricedInfo.of(Sets.newHashSet(), null);
        });
    }


    private static class ProceedingJoinPointHolder {
        private ProceedingJoinPoint pjp;
        private Method method;

        public ProceedingJoinPointHolder(ProceedingJoinPoint pjp) {
            this.pjp = pjp;
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            this.method = signature.getMethod();
        }

        @Override
        public int hashCode() {
            return method.hashCode();
        }

        @Override
        public boolean equals(Object another) {
            if (this == another) {
                return true;
            }
            if (!(another instanceof ProceedingJoinPointHolder)) {
                return false;
            }
            ProceedingJoinPointHolder that = (ProceedingJoinPointHolder) another;
            return this.method.equals(that.method);
        }
    }

    private static class MetricedInfo {
        private Set<Metriced> metriceds;
        private Method annotatedMethd;

        public static MetricedInfo of(Set<Metriced> metriceds, Method annotatedMethd) {
            MetricedInfo info = new MetricedInfo();
            info.metriceds = metriceds;
            info.annotatedMethd = annotatedMethd;
            return info;
        }

        public Set<Metriced> getMetriceds() {
            return metriceds;
        }

        public Method getAnnotatedMethd() {
            return annotatedMethd;
        }
    }
}
