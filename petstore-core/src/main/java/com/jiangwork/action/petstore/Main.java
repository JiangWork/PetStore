package com.jiangwork.action.petstore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jiang on 2018/8/28.
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService completionService = new ExecutorCompletionService(executorService);
        executorService.submit(() -> System.out.println("hi"));
        executorService.submit(() -> {
            System.out.println("ds");
            return 0;
        });

        Map<String, String> map = new HashMap<>();
        map.put("sd", "dsd");
        System.out.println(map.get(new String("sd")));

//        Lists.partition()
    }
}
