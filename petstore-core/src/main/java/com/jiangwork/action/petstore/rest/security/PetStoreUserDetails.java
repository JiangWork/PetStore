package com.jiangwork.action.petstore.rest.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.jiangwork.action.petstore.model.User;

public class PetStoreUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;
    
    public PetStoreUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        // TODO Auto-generated constructor stub
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
