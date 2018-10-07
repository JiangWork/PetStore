package com.jiangwork.action.petstore.metrics;

import java.lang.annotation.*;

/**
 * Created by Jiang on 2018/10/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Metrics {

    Metriced[] value();
}
