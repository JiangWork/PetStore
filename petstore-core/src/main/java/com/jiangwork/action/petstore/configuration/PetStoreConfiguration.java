package com.jiangwork.action.petstore.configuration;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.jiangwork.action.petstore.rest.security.PetStoreAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;
import java.util.concurrent.CountDownLatch;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)
public class PetStoreConfiguration extends WebSecurityConfigurerAdapter {
    //https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
    @Autowired
    private PetStoreAuthenticationProvider authenticationProvider;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //        http.authorizeRequests()
        //                .antMatchers("/**/search", "/**/feedback").permitAll()
        //                .antMatchers("/test/**").permitAll()
        //                .antMatchers("/admin/**").permitAll()
        //      .antMatchers("/**").hasRole("USER")
        //                .and().csrf().disable().
        //                httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // see https://docs.spring.io/spring-security/site/docs/5.0.5.RELEASE/reference/htmlsingle/#jc-authorize-requests

        http.authorizeRequests()
        .antMatchers("/**/search", "/**/feedback").permitAll()
        .antMatchers("/test/**").permitAll()
        .antMatchers("/admin/**").permitAll()
        .antMatchers("/**").hasRole("USER")
        .and().csrf().disable().
        httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HashFunction hashFunction() {
        return Hashing.murmur3_128();
    }


    @Bean
    public MutableAclService aclService(DataSource dataSource, LookupStrategy lookupStrategy, SpringCacheBasedAclCache cache) {
        return new JdbcMutableAclService(dataSource, lookupStrategy, cache);
    }

    @Bean
    public SpringCacheBasedAclCache aclCache(ConsoleAuditLogger consoleAuditLogger) {
        return new SpringCacheBasedAclCache(new ConcurrentMapCache("aclCache"),
                new DefaultPermissionGrantingStrategy(consoleAuditLogger),
                new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ACL_ADMIN")));
    }

    @Bean
    public ConsoleAuditLogger consoleAuditLogger() {
        return new ConsoleAuditLogger();
    }

    @Bean
    public LookupStrategy lookupStrategy(DataSource ds, SpringCacheBasedAclCache cache, ConsoleAuditLogger consoleAuditLogger) {
        return new BasicLookupStrategy(ds, cache, new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")),
                consoleAuditLogger);
    }

    @Bean
    public PermissionEvaluator permissionEvaluator(AclService aclService) {
        return new AclPermissionEvaluator(aclService);
    }

    @Bean
    public CountDownLatch countDownLatch() {
        return new CountDownLatch(1);
    }


    // following is autoconfigured by Spring boot
//    @Bean
//    @ConfigurationProperties(prefix="hikari.datasource")
//    public DataSource dataSource() {
//        DataSource ds = DataSourceBuilder.create().build();
//        return ds;
//    }


//
//    @Bean
//    public PlatformTransactionManager transactionManager(){
////        return getDataSourceTransactionManager();
//        return getJpaTransactionManager();
//    }
//
//    private PlatformTransactionManager getDataSourceTransactionManager() {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
//        dataSourceTransactionManager.setDataSource(dataSource);
//        return dataSourceTransactionManager;
//    }
//
//    private PlatformTransactionManager getJpaTransactionManager() {
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setDataSource(dataSource);
//        System.out.println(">>>>>>>>>" + dataSource);
//        return jpaTransactionManager;
//    }
}
