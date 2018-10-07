package com.jiangwork.action.petstore.rest;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.google.common.base.Throwables;
import com.jiangwork.action.petstore.metrics.Metriced;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
//@Controller
//@RequestMapping(value = "/domain")
public class SimpleController {


    @RequestMapping(value = "/hello/{name}", method = {RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    @Metriced(clazz = Meter.class, name = "requests")
    @Metriced(clazz = Histogram.class, name = "response-time")
    @Metriced(clazz = Counter.class, name = "request-total")
    @Metriced(clazz = Gauge.class, name = "request-executing")
    public String hello(@PathVariable("name") String name) {
//    	HandlerInterceptor
//    	RequestMappingHandlerAdapter
//    	SimpleMappingExceptionResolver
        try {
            Thread.sleep(new Random().nextInt(10000));
            String ret = String.format("%s: Hello %s", LocalDateTime.now(), name);
            return ret;
        } catch (Exception e) {
            return Throwables.getStackTraceAsString(e);
        }
    }

    @RequestMapping(value = "/hello/chinses/{name}", method = {RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    @Metriced(clazz = Counter.class, name = "request-total")
    public String helloChinese(@PathVariable("name") String name) {
//    	HandlerInterceptor
//    	RequestMappingHandlerAdapter
//    	SimpleMappingExceptionResolver
        try {
            Thread.sleep(new Random().nextInt(10000));
            String ret = String.format("%s: Hello Chinese %s", LocalDateTime.now(), name);
            return ret;
        } catch (Exception e) {
            return Throwables.getStackTraceAsString(e);
        }
    }
}
