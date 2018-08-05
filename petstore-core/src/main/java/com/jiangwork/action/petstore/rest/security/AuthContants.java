package com.jiangwork.action.petstore.rest.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthContants {

    public static final String HAS_USER_ROLE = "hasRole('ROLE_USER')";
    public static final String HAS_ADMIN_ROLE = "hasRole('ROLE_ADMIN')";
    public static final String HAS_SERVICE_ROLE = "hasRole('ROLE_SERVICE')";
    
    public static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
