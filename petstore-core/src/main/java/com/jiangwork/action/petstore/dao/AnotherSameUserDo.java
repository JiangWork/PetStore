package com.jiangwork.action.petstore.dao;

import javax.persistence.*;

@Entity
@Table(name="user")
public class AnotherSameUserDo {



    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String username;
    private long sign;
    private String email;
    private String password = "";
    private String roles;

    @PrePersist
    public void prePersist() {
        System.out.println("Preperisst");
    }
    public AnotherSameUserDo() {}
    
    public AnotherSameUserDo(long id, String username, String email, String roles) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public AnotherSameUserDo(String username, String email,  String roles) {
        super();
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    
    @Override
    public String toString() {
        return String.format("User[id=%d, username='%s',email=%s]", id, username, email);
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

    public long getSign() {
        return sign;
    }

    public void setSign(long sign) {
        this.sign = sign;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
