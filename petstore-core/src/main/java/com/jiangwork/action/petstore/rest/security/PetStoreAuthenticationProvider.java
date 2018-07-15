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
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

@Component
public class PetStoreAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOG = LoggerFactory.getLogger(PetStoreAuthenticationProvider.class);
	
	private static final Cache<String, Authentication> AUTH_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .initialCapacity(100)
            .expireAfterWrite(3, TimeUnit.HOURS).build();
	
	@Autowired
	private PetStoreUserDetailsService petStoreUserDetailsService;
	
	private HashFunction hf = null;
    
    public PetStoreAuthenticationProvider() {
        hf = Hashing.murmur3_128();
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
		// TODO Auto-generated method stub
		final UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = userToken.getName();
        String password = (String) authentication.getCredentials();
        LOG.debug("Authenticating {}:{}.", username, password);
        UserDetails userDetails = petStoreUserDetailsService.loadUserByUsername(username);
        Authentication authed;
        if(userDetails.getPassword().equals(password)) {
        	authed = createSuccessfulAuthentication(userToken, userDetails);
        	SecurityContextHolder.getContext().setAuthentication(authed);
        	System.out.println("Authed");
        } else {
        	throw new BadCredentialsException("Incorrect password or account."); 
        }
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
