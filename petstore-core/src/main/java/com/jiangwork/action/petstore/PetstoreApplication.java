package com.jiangwork.action.petstore;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jiangwork.action.petstore.dao.UserDO;
import com.jiangwork.action.petstore.dao.UserRepository;

@SpringBootApplication
public class PetstoreApplication {

    @Autowired
    private ApplicationContext context;
    private static final Logger log = LoggerFactory.getLogger(PetstoreApplication.class);
    
	public static void main(String[] args) {
		SpringApplication.run(PetstoreApplication.class, args);
	}
	
	
	@Bean
    public CommandLineRunner demo(UserRepository repository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            Arrays.asList(context.getBeanDefinitionNames()).stream().forEach((name)->{
                System.out.println("Bean>" + name + ":" + context.getBean(name));
            });
            // save a couple of customers
            repository.save(new UserDO(1, "jiangzhao", System.currentTimeMillis(), "abc@email.com", passwordEncoder.encode("123"), "user,admin"));
            repository.save(new UserDO(2, "jackson", System.currentTimeMillis(), "abc@email.com", passwordEncoder.encode("123"), "user"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (UserDO customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            repository.findById(1L)
                .ifPresent(customer -> {
                    log.info("Customer found with findById(1L):");
                    log.info("--------------------------------");
                    log.info(customer.toString());
                    log.info("");
                });

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByUsername("jiangzhao").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            //  log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}
