package com.jiangwork.action.petstore;

import com.jiangwork.action.petstore.rest.security.acl.PetStoreIdentity;

import java.io.Serializable;

public class User implements PetStoreIdentity {
    private long id;
    private String username;
    private String email;
    private String roles;
    private String password;
    private String sign;
    
    public User() {
        
    }
    
    public User(String username, String email, String roles, String password, String sign) {
        super();
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.sign = sign;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return String.format("User[id=%d, username='%s', sign=%d, email=%s]", id, username, sign, email);
    }
    
    public static User of(String name) {
        User user = new User();
        user.username = name;
        return user;
    }

    @Override
    public Serializable getIdentifier() {
        return id;
    }

    @Override
    public String getType() {
        return User.class.getCanonicalName();
    }
}