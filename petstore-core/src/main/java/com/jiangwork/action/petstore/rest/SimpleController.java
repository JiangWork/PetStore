package com.jiangwork.action.petstore.rest;

import com.google.common.base.Throwables;
import com.jiangwork.action.petstore.metrics.Metriced;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

import static com.jiangwork.action.petstore.metrics.MetricType.*;

@RestController
//@Controller
//@RequestMapping(value = "/domain")
public class SimpleController {


    @RequestMapping(value = "/hello/{name}", method = {RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    @Metriced(type = METER, name = "requests")
    @Metriced(type = HISTOGRAM, name = "response-time")
    @Metriced(type = COUNTER, name = "request-total")
    @Metriced(type = GAUGE, name = "request-executing")
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
    @Metriced(type = COUNTER, name = "request-total")
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
