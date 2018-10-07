package com.jiangwork.action.petstore;

import com.jiangwork.action.petstore.dao.AnotherSameUserRepository;
import com.jiangwork.action.petstore.dao.UserDO;
import com.jiangwork.action.petstore.dao.UserRepository;
import com.jiangwork.action.petstore.rest.service.TransactionalUserTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class PetstoreApplication {

    private static final Logger log = LoggerFactory.getLogger(PetstoreApplication.class);
    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(PetstoreApplication.class, args);
    }


//	@Bean
//    @Transactional
//    public CommandLineRunner demo(UserRepository repository, AnotherSameUserRepository repo, PasswordEncoder passwordEncoder,
//                                  MutableAclService aclService) {
//        return (args) -> {
//            Arrays.asList(context.getBeanDefinitionNames()).stream().forEach((name)->{
//                System.out.println("Bean>" + name + ":" + context.getBean(name));
//            });
//            // save a couple of customers
//            repository.save(new UserDO(1, "jiangzhao", System.currentTimeMillis(), "abc@email.com", passwordEncoder.encode("123"), "user,admin"));
//            repository.save(new UserDO(2, "jackson", System.currentTimeMillis(), "abc@email.com", passwordEncoder.encode("123"), "user"));
//
//            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (UserDO customer : repository.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");
//
//         // fetch all customers
//            repo.save(new AnotherSameUserDo(5, "another user", "email@email.com", "roles"));
//            log.info("Customers found with AnotherSameUserRepository():");
//            log.info("-------------------------------");
//            for (AnotherSameUserDo customer : repo.findAll()) {
//                log.info(customer.toString());
//            }
//
//            log.info("");
//
//            // fetch an individual customer by ID
//            repository.findById(1L)
//                .ifPresent(customer -> {
//                    log.info("Customer found with findById(1L):");
//                    log.info("--------------------------------");
//                    log.info(customer.toString());
//                    log.info("");
//                });
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            repository.findByUsername("jiangzhao").forEach(bauer -> {
//                log.info(bauer.toString());
//            });
//            // for (Customer bauer : repository.findByLastName("Bauer")) {
//            //  log.info(bauer.toString());
//            // }
//            log.info("");
//            SecurityContextHolder.getContext().setAuthentication(
//                    new UsernamePasswordAuthenticationToken("jiangzhao", "jiangzhao",
//                            Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER"))));
//            ObjectIdentity oi = new ObjectIdentityImpl(User.class, 2);
//            Sid sid = new PrincipalSid("jiangzhao");
//            Permission p = BasePermission.ADMINISTRATION;
//            // Create or update the relevant ACL
//            MutableAcl acl = null;
//            try {
//                acl = (MutableAcl) aclService.readAclById(oi);
//            } catch (NotFoundException nfe) {
//                acl = aclService.createAcl(oi);
//            }
//
//            // Now grant some permissions via an access control entry (ACE)
//            acl.insertAce(acl.getEntries().size(), p, sid, true);
//            aclService.updateAcl(acl);
//
//            acl = (MutableAcl) aclService.readAclById(oi);
//
//        };
//    }

    @Bean
    public CommandLineRunner transactionalAndTemplateMixTest(final TransactionalUserTestCase transactionalUserTestCase, CountDownLatch countDownLatch) {
        return args -> {
//            transactionalUserTestCase.test2();
//            new Thread(()->transactionalUserTestCase.test3()).start();
//            new Thread(()->transactionalUserTestCase.test3()).start();
//            countDownLatch.countDown();
            transactionalUserTestCase.transactionalAndTemplateMixTest();

        };

    }

    @Bean
    public CommandLineRunner demo(UserRepository repository, AnotherSameUserRepository repo, PasswordEncoder passwordEncoder,
                                  MutableAclService aclService) {
        return (args) -> {
            Arrays.asList(context.getBeanDefinitionNames()).stream().forEach((name) -> {
                System.out.println("Bean>" + name + ":" + context.getBean(name));
            });
            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (UserDO customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");
        };
    }

}
