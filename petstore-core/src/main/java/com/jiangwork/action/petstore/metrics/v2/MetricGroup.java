package com.jiangwork.action.petstore.metrics.v2;

/**
 * Created by Jiang on 2018/11/4.
 */
public enum MetricGroup {
    DEFAULT("default", "default");

    private String name;
    private String displayName;

    MetricGroup(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}
