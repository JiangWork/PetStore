package com.jiangwork.action.petstore.metrics;

import com.codahale.metrics.MetricRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Jiang on 2018/10/7.
 */
@Component
@RestController
public class PrometheusExporter implements InitializingBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrometheusExporter.class);
    @Autowired
    private MetricRegistry metricRegistry;


    @Override
    public void afterPropertiesSet() throws Exception {
        new DropwizardExports(metricRegistry).register();
        DefaultExports.initialize();
    }

    // work around
    @RequestMapping(value = "/metrics", method = {RequestMethod.GET}, produces = {"text/plain"})
    public String metrics() {
        StringWriter sw = new StringWriter();
        try {
            TextFormat.write004(sw, CollectorRegistry.defaultRegistry.metricFamilySamples());
        } catch (IOException e) {
            // should not happen
            LOGGER.error("Failed to write metrics to Prometheus.", e);
        }
        return sw.toString();
    }
}
