package com.jiangwork.action.petstore.metrics.v2;

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

    /**
     * Define a metric which detailed definition defined in class {@link Metric}
     * Since we want define all metric in one place so this annotation won't directly define
     * any metric metadata.
     *
     * @return
     */
    Metric ref();
}
