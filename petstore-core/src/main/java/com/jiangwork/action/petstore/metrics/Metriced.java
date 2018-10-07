package com.jiangwork.action.petstore.metrics;

import java.lang.annotation.*;

/**
 * A marker that needs to log the metrics
 * Created by Jiang on 2018/10/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Metrics.class)
public @interface Metriced {

    String name();

    MetricType type() default MetricType.COUNTER;
}
