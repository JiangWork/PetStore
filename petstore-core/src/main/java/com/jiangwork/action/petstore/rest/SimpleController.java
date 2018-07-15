package com.jiangwork.action.petstore.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
//@RequestMapping(value = "/domain")
public class SimpleController {

    
    @RequestMapping(value = "/hello", method = { RequestMethod.GET }, produces = { "application/json" })
    @ResponseBody
	public String hello() {
//    	HandlerInterceptor
//    	RequestMappingHandlerAdapter
//    	SimpleMappingExceptionResolver
		return "Hello world!";
		
	}
}
