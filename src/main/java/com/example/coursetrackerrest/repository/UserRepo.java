package com.example.coursetrackerrest.repository;

//import com.example.authenticate.model.Users;
import com.example.coursetrackerrest.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

}
