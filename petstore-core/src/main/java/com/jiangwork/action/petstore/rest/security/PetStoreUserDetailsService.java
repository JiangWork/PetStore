package com.jiangwork.action.petstore.rest.security;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jiangwork.action.petstore.rest.service.UserService;

@Component
public class PetStoreUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(PetStoreUserDetailsService.class);
    
    @Autowired
    private UserService userService;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Optional<com.jiangwork.action.petstore.User> user = userService.findUser(username);
		if(user.isPresent()) {
		    com.jiangwork.action.petstore.User userObj = user.get();
		    Collection<? extends GrantedAuthority> authorities = StringUtils.commaDelimitedListToSet(userObj.getRoles())
		            .stream()
		            .map((role)->new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
		            .collect(Collectors.toList());
		    PetStoreUserDetails userDetails = new PetStoreUserDetails(username, userObj.getPassword(), authorities);
		    LOG.info("Loaded user {} with authorities {}.", username, authorities);
		    return userDetails;
		} else {
			throw new UsernameNotFoundException("cannot find user " + username);
		}	    
	}

}
