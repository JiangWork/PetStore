package com.jiangwork.action.petstore.rest;

import com.jiangwork.action.petstore.User;
import com.jiangwork.action.petstore.metrics.Metriced;
import com.jiangwork.action.petstore.rest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.jiangwork.action.petstore.metrics.MetricType.COUNTER;

@RestController
@RequestMapping(value="/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method = { RequestMethod.POST }, produces = { "application/json" })
    @Metriced(type = COUNTER, name = "request-total")
    public long addUser(@RequestBody User user) {
        LOG.info("adding user {}.", user);
        return userService.addUser(user);
    }
    
    @RequestMapping(method = { RequestMethod.GET }, produces = { "application/json" })
    public UserEntities.UserResponse allUser() {
        List<User> users = userService.getAllUser();
        UserEntities.UserResponse response = new UserEntities.UserResponse();
        response.setCount(users.size());
        response.setUsers(users);
        return response;
    }
    
    @RequestMapping(value= "/{id}", method = { RequestMethod.GET }, produces = { "application/json" })
    public UserEntities.UserResponse getUserOf(@PathVariable("id") long id) {
        Optional<User> user = userService.findUser(id);
        UserEntities.UserResponse response = new UserEntities.UserResponse();
        response.setCount(user.isPresent()?1:0);
        response.setUsers(Arrays.asList(user.orElse(null)));
        return response;
    }
    
    @RequestMapping(value= "/{username}", method = { RequestMethod.DELETE }, produces = { "application/json" })
    public long deleteUser(@PathVariable("username") String username) {
        return userService.deleteUser(User.of(username));
    }
    

}
