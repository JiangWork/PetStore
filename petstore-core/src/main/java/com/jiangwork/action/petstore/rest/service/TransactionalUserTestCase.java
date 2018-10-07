package com.jiangwork.action.petstore.rest.service;

import com.jiangwork.action.petstore.dao.UserDO;
import com.jiangwork.action.petstore.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jiang on 2018/8/21.
 */
@Component
public class TransactionalUserTestCase {
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CountDownLatch countDownLatch;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void test() {
        System.out.println("begin find");
        UserDO dao = userRepository.findById(1L).get();
        System.out.println("begin set");
        dao.setEmail("ab" + LocalDateTime.now());
        System.out.println("begin save");
        userRepository.save(dao);
    }

    @Transactional
    public void test2() {
        UserDO dao = entityManager.find(UserDO.class, 1L);
        System.out.println("1:" + dao);
        new Thread(() -> {
            transactionTemplate.execute((status) -> {
                UserDO dao1 = entityManager.find(UserDO.class, 1L);
                dao1.setEmail("new email1 " + Thread.currentThread() + LocalDateTime.now());
                entityManager.merge(dao1);
                entityManager.flush();
                System.out.println("2:" + entityManager.find(UserDO.class, 1L));
                System.out.println(Thread.currentThread());
                return true;
            });

        }).start();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread());
        dao.setEmail("new email2 " + LocalDateTime.now());
        entityManager.persist(dao);
        System.out.println("3:" + entityManager.find(UserDO.class, 1L));
//        dao.setEmail("new email3");
//        entityManager.merge(dao);
    }

    @Transactional
    public void test3() {
        Optional<UserDO> dao = userRepository.findById(1L);
        System.out.println("Find dao:" + System.identityHashCode(dao));
        UserDO userDO = dao.get();
        userDO.setEmail("test3 " + Thread.currentThread() + LocalDateTime.now());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userRepository.saveAndFlush(userDO);
        System.out.println(Thread.currentThread() + ": " + userDO + " saved.");
    }

    @Transactional
    public void transactionalAndTemplateMixTest() {
        Optional<UserDO> dao = userRepository.findById(1L);
        System.out.println("Find dao:" + System.identityHashCode(dao));
        UserDO userDO = dao.get();
        String newEmail = "test3 " + Thread.currentThread() + LocalDateTime.now();
        userDO.setEmail(newEmail);
        System.out.println("new email: " + newEmail);
        userRepository.save(userDO);
        transactionTemplate.execute((status) -> {
            UserDO dao1 = userRepository.findById(2L).get();
            dao1.setEmail("new email1 " + Thread.currentThread() + LocalDateTime.now());
            userRepository.save(dao1);
            System.out.println("new email: " + dao1.getEmail());
            throw new RuntimeException("Fake");
        });

    }


}
