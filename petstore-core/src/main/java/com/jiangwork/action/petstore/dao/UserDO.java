package com.jiangwork.action.petstore.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserDO {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    private String username;
    private long sign;
    private String email;
    private String password;
    private String roles;
    
    public UserDO() {}
    
    public UserDO(long id, String username, long sign, String email, String password, String roles) {
        super();
        this.id = id;
        this.username = username;
        this.sign = sign;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserDO(String username, long sign, String email, String password, String roles) {
        super();
        this.username = username;
        this.sign = sign;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    
    @Override
    public String toString() {
        return String.format("User[id=%d, username='%s', sign=%d, email=%s, password=%s]", id, username, sign, email, password);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getSign() {
        return sign;
    }

    public void setSign(long sign) {
        this.sign = sign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    
}
