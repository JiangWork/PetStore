package com.jiangwork.action.petstore.metrics.v2;

import com.codahale.metrics.MetricRegistry;

/**
 * Created by Jiang on 2018/10/7.
 */
public enum MetricType {
    COUNTER {
        public void register(MetricRegistry registry, String name) {
            registry.counter(name);
        }
    },
    GAUGE,
    HISTOGRAM {
        public void register(MetricRegistry registry, String name) {
            registry.histogram(name);
        }
    },
    METER {
        public void register(MetricRegistry registry, String name) {
            registry.meter(name);
        }
    };

    /**
     * Register the metric to {@link MetricRegistry}
     *
     * @param registry
     * @param name
     */
    public void register(MetricRegistry registry, String name) {
        throw new AbstractMethodError();
    }


}
