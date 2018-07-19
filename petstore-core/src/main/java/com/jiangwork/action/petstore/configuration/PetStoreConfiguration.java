package com.jiangwork.action.petstore.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.jiangwork.action.petstore.rest.security.PetStoreAuthenticationProvider;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)
public class PetStoreConfiguration extends WebSecurityConfigurerAdapter {
    //https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
    @Autowired
    private PetStoreAuthenticationProvider authenticationProvider;

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
        httpBasic(); 
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
    @ConfigurationProperties("app.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
