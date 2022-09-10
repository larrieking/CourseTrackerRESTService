package com.example.coursetrackerrest.repository;

//import com.example.authenticate.model.Role;
import com.example.coursetrackerrest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Rolerepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
