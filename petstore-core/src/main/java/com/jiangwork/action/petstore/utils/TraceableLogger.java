package com.jiangwork.action.petstore.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.AclEntryVoter;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;

public class TraceableLogger {
    private static final ThreadLocal<AtomicLong> THREAD_LOCAL_LONG = ThreadLocal.withInitial(()->new AtomicLong());
    private static final ThreadLocal<String> THREAD_LOCAL = ThreadLocal.withInitial(()->{
        return System.identityHashCode(Thread.currentThread())
         + "-" + THREAD_LOCAL_LONG.get().incrementAndGet();
    });
    
    private final Logger logger;
    public TraceableLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }
    
    public void debug(String format, Object... args) {
        logger.debug(THREAD_LOCAL.get() + ": " + format, args);
    }
    
    public void info(String format, Object... args) {
        System.out.println(String.format(THREAD_LOCAL.get() + ": " + format, args));
    }
    
    public void error(String format, Object... args) {
        logger.error(THREAD_LOCAL.get() + ": " + format, args);
    }
    
    public void reset() {
        THREAD_LOCAL.remove();
    }
    
    
    
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        Task aTask = new Task("Taska");
        Task bTask = new Task("Taskb");
        es.submit(aTask);
        es.submit(aTask);
        es.submit(bTask);
        es.submit(bTask);

    }
    
    public static class Task implements Runnable {
        TraceableLogger logger = new TraceableLogger(Task.class);
        String id;

        public Task(String id) {
            System.out.println("hi" + id);
            this.id = id;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            logger.reset();
            logger.info(id + ":" + Thread.currentThread() + ": this is in run {}", 1);
            echo();
        }
        
        public void echo() {
            logger.info(id + ":" + Thread.currentThread() + ": this is in echo {}", 1);
        }
    }
}
