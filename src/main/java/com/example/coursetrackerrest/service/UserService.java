package com.example.coursetrackerrest.service;

//import com.example.authenticate.model.Role;
//import com.example.authenticate.model.Users;
import com.example.coursetrackerrest.entity.Role;
import com.example.coursetrackerrest.entity.Users;

import java.util.List;


public interface UserService {
    Users saveUser(Users user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    Users getUser(String username);
    List<Users> getUsers();

}
