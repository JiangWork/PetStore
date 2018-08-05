package com.jiangwork.action.petstore.rest.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.jiangwork.action.petstore.dao.UserDO;

public class PetStoreUserDetails extends org.springframework.security.core.userdetails.User {

    private UserDO user;
    
    public PetStoreUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        // TODO Auto-generated constructor stub
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }
    
    
}
