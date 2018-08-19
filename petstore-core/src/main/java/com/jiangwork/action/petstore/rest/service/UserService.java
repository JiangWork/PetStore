package com.jiangwork.action.petstore.rest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.jiangwork.action.petstore.User;
import com.jiangwork.action.petstore.dao.UserDO;
import com.jiangwork.action.petstore.dao.UserRepository;
import com.jiangwork.action.petstore.rest.security.AuthContants;

@Service
public class UserService {

    private static final List<String> ALLOWED_ROLES = Arrays.asList(new String[] {"user", "admin"});
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * 
     * @param user
     * @throws IllegalArgumentException
     * @return the user id
     */
    @PreAuthorize(AuthContants.HAS_ADMIN_ROLE)
    public long addUser(User user) {
        Assert.hasLength(user.getUsername(), "username must be provided.");
        List<UserDO> users = userRepository.findByUsername(user.getUsername());
        Assert.isTrue(users==null || users.size()==0, "User " + user.getUsername() + " is already exists.");
        String[] roles = StringUtils.commaDelimitedListToStringArray(user.getRoles());
        for(String role: roles) {
            Assert.isTrue(ALLOWED_ROLES.contains(role), "Roles must be one of " + ALLOWED_ROLES);
        }
        UserDO userDO = initializeFrom(user);
        UserDO savedUser = userRepository.save(userDO);
        return savedUser.getId();
    }
    
    @PreAuthorize(AuthContants.HAS_ADMIN_ROLE + " or #U.username == authentication.name" +
            " or hasPermission(#project, 'DELETE')")
    public long deleteUser(@P("U") User user) {
        Optional<UserDO> userDO = findUserInternal(user.getUsername());
        if(userDO.isPresent()) {
            userRepository.delete(userDO.get());
            return userDO.get().getId();
        }
        return -1;
    }
    
    @PreAuthorize(AuthContants.HAS_ADMIN_ROLE)
    public List<User> getAllUser() {
        Iterable<UserDO> userDOs =  userRepository.findAll();
        List<User> users = new ArrayList<>();
        userDOs.forEach((userDO)-> {
            users.add(initializeFrom(userDO));
        });
        return users;
    }
    
    public Optional<User> findUser(String username) {
        Optional<UserDO> userDO = findUserInternal(username);
        return userDO.isPresent()? Optional.of(initializeFrom(userDO.get())) : Optional.empty();
    }
    
    @PostAuthorize(AuthContants.HAS_ADMIN_ROLE + " or returnObject.orElse(null) == null "
            + "or returnObject.get().getUsername() == authentication.name" )
    public Optional<User> findUser(long id) {
        Optional<UserDO> userDO = findUserInternal(id);
        return userDO.isPresent()? Optional.of(initializeFrom(userDO.get())) : Optional.empty();
    }  
    
    private Optional<UserDO> findUserInternal(String username) {
        List<UserDO> users = userRepository.findByUsername(username);
        return users==null||users.isEmpty()? Optional.empty() : Optional.of(users.get(0));
    }
    
    private Optional<UserDO> findUserInternal(long id) {
        return userRepository.findById(id);
    }  
    
    public UserDO initializeFrom(User user) {
        UserDO userDO = new UserDO(user.getUsername(), System.currentTimeMillis(), 
                user.getEmail(), passwordEncoder.encode(user.getPassword()), user.getRoles());
        return userDO;
    }
    
    public User initializeFrom(UserDO userDO) {
        User user = new User(userDO.getUsername(), userDO.getEmail(), userDO.getRoles(), 
                userDO.getPassword(), new Date(userDO.getSign()).toString());
        user.setId(userDO.getId());
        return user;       
    }
}
