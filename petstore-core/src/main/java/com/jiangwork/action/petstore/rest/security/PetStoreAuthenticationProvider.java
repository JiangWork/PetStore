package com.jiangwork.action.petstore.rest.security;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.HashFunction;

@Component
public class PetStoreAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(PetStoreAuthenticationProvider.class);

    private static final Cache<String, Authentication> AUTH_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .initialCapacity(100)
            .expireAfterWrite(3, TimeUnit.HOURS).build();

    @Autowired
    private PetStoreUserDetailsService petStoreUserDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HashFunction hf;

    public PetStoreAuthenticationProvider() {
    }

    public Authentication getAuthentication(final String hashKey) {
        Authentication authed = null;
        try {
            authed = AUTH_CACHE.get(hashKey, new Callable<Authentication>() {
                @Override
                public Authentication call() throws Exception {
                    throw new ExecutionException("no such key " + hashKey, null);
                }
            });
            LOG.debug("Hitting cache: {}.", hashKey);
        } catch (ExecutionException e) {
            // ignored, no such key
        }
        return authed;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                "Only UsernamePasswordAuthenticationToken is supported");
        final UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = userToken.getName();
        String password = (String) userToken.getCredentials();

        LOG.debug("Authenticating {}:{}.", username, passwordEncoder.encode(password));
        System.out.println(String.format("Authenticating %s:%s.", username, passwordEncoder.encode(password)));

        StringBuilder hashStringBuilder = new StringBuilder(username + ":" + password);
        String hashKey = hf.hashUnencodedChars(hashStringBuilder.toString()).toString();

        Authentication authed = getAuthentication(hashKey);
        if(authed == null) {
            LOG.info("Processing new authentication request for user: {}.", username);

            if (!StringUtils.hasLength(username)) {
                throw new BadCredentialsException("Empty username.");
            }
            if (!StringUtils.hasLength(password)) {
                throw new BadCredentialsException("Empty Password");
            }
            Assert.notNull(password, "Null password was supplied in authentication token");
            authed = doAuthentication(userToken);
            AUTH_CACHE.put(hashKey, authed);
            LOG.info("Authenticated user: {}. ", username);
        }
        SecurityContextHolder.getContext().setAuthentication(authed);
        return authed;
    }


    private Authentication doAuthentication(UsernamePasswordAuthenticationToken userToken) {
        String username = userToken.getName();
        String password = (String) userToken.getCredentials();
        UserDetails user = petStoreUserDetailsService.loadUserByUsername(username);
        boolean access = passwordEncoder.matches(password, user.getPassword());
        if(!access) {
            throw new BadCredentialsException("Incorrect password or account.");
        }
        Authentication authed = createSuccessfulAuthentication(userToken, user);
        return authed;
    }


    protected Authentication createSuccessfulAuthentication(
            UsernamePasswordAuthenticationToken authentication, UserDetails user) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(), authentication.getCredentials(), user.getAuthorities());
        result.setDetails(user);
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
