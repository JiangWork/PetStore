package com.jiangwork.action.petstore.rest.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PetStoreUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if("jiangzhao".equals(username)) {
			return User.withUsername("jiangzhao").password("123").roles("USER", "ADMIN").build();
		} else {
			throw new UsernameNotFoundException("cannot find user " + username);
		}
	}

}
