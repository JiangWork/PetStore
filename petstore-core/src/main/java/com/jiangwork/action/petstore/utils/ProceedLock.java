package com.jiangwork.action.petstore.utils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jiang on 2018/9/2.
 */
public class ProceedLock {

    private Map<LockKey, CountableLock> lockStore = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ProceedLock proceedLock = new ProceedLock();
        LockKey key = LockKey.of("test", "user1");
        LockKey key2 = LockKey.of("test", "user2");
        Runnable runnable = () -> {
            proceedLock.acquireLock(key);
            System.out.println(Thread.currentThread() + ":" + key + ": get the lock and working");
            System.out.println(Thread.currentThread() + ":" + key + ": releasing the lock.");
            proceedLock.releaseLock(key);
        };
        Runnable runnable2 = () -> {
            proceedLock.acquireLock(key2);
            System.out.println(Thread.currentThread() + ":" + key2 + ": get the lock and working");
            System.out.println(Thread.currentThread() + ":" + key2 + ": releasing the lock.");
            proceedLock.releaseLock(key2);
        };
        int threadNum = 20;
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; ++i) {
            if (i % 2 == 0) threads[i] = new Thread(runnable);
            else threads[i] = new Thread(runnable2);
        }
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(proceedLock.lockStore.size());
    }

    /**
     * Acquire the lock specified by LockKey.
     * Block if there is no lock available.
     * <p>
     * The calling thread must have the lock after return.
     *
     * @param key
     */
    public void acquireLock(LockKey key) {
        CountableLock lock = getLockObject(key);
        lock.lock();
    }

    /**
     * Return the lock and check if we can remove
     * the lock object from Lock Store if there is no pending threads.
     * <p>
     * Must called by the lock owner.
     *
     * @param key
     */
    public void releaseLock(LockKey key) {
        CountableLock lock = releaseLockObject(key);
        lock.unlock();
    }

    private synchronized CountableLock getLockObject(LockKey key) {
        CountableLock lock = lockStore.computeIfAbsent(key, k -> new CountableLock());
        lock.increaseCount();
        return lock;
    }

    private synchronized CountableLock releaseLockObject(LockKey key) {
        CountableLock lock = lockStore.get(key);
        if (lock.decreaseCount() == 0) {
            lockStore.remove(key);
        }
        return lock;
    }

    public static class LockKey {
        private String action;  // 操作类型
        private String target;  // 操作对象

        public static LockKey of(String action, String target) {
            LockKey key = new LockKey();
            key.action = action;
            key.target = target;
            return key;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LockKey lockKey = (LockKey) o;

            if (action != null ? !action.equals(lockKey.action) : lockKey.action != null) return false;
            return target != null ? target.equals(lockKey.target) : lockKey.target == null;

        }

        @Override
        public int hashCode() {
            return Objects.hash(action, target);
        }
    }

    private class CountableLock extends ReentrantLock {
        private int count;

        public CountableLock() {
        }

        public void increaseCount() {
            count++;
        }

        public int decreaseCount() {
            count--;
            return count;
        }
    }
}
