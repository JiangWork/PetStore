package com.jiangwork.action.petstore.rest;

import java.util.List;

import com.jiangwork.action.petstore.User;

public class UserEntities {

    public static class UserResponse {
        private int count;
        private List<User> users;
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
        public List<User> getUsers() {
            return users;
        }
        public void setUsers(List<User> users) {
            this.users = users;
        }
        
    }
}
