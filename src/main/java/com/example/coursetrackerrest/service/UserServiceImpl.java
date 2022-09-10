package com.example.coursetrackerrest.service;

import com.example.coursetrackerrest.entity.Role;
import com.example.coursetrackerrest.entity.Users;
import com.example.coursetrackerrest.repository.Rolerepo;
import com.example.coursetrackerrest.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
   @Autowired
    private final UserRepo userRepo;

   @Autowired
    private final Rolerepo rolerepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Users users = userRepo.findByUsername(username);
        Users user = userRepo.findByUsername(username);
        if(user ==null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found in the DB");
        }else{
            log.info("User found");
        }
        Collection<SimpleGrantedAuthority>authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    @Override
    public Users saveUser(Users user) {
        log.info("saving new user to the db");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("savin new {} role to the db", role.getName());
        return rolerepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding roele {} to user {}", roleName, username);
        Users user = userRepo.findByUsername(username);
        Role role = rolerepo.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public Users getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<Users> getUsers() {
        log.info("fetching all users");
        return userRepo.findAll();
    }


}
