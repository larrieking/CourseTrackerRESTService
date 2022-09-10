package com.example.coursetrackerrest;

import com.example.coursetrackerrest.entity.Course;
import com.example.coursetrackerrest.entity.Role;
import com.example.coursetrackerrest.entity.Users;
import com.example.coursetrackerrest.service.ServiceImpl.CourseServiceImpl;
import com.example.coursetrackerrest.service.UserService;
import com.example.coursetrackerrest.service.UserServiceImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class CourseTrackerRestApplication  {


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public OpenAPI custtomOpenApi(@Value("${app.description}") String appDescription, @Value("${app.version}") String appVersion){
        return new OpenAPI().info(new Info().title("Course Tracker API").version(appVersion)
                .description(appDescription).termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
    public static void main(String[] args) {
        SpringApplication.run(CourseTrackerRestApplication.class, args);
    }

    @Autowired
    private CourseServiceImpl courseService;

    @Bean
    CommandLineRunner run(UserServiceImpl userService){
        return args -> {
            Course course = Course.builder()
                    .name("Java")
                    .description("A new Java Book")
                    .rating(5)
                    .category("Java")
                    .build();

            courseService.createCourse(course);
            System.out.println("Success");

            userService.saveRole(new Role("Role_User"));
            userService.saveRole(new Role("Role_Manager"));
            userService.saveRole(new Role("Role_Admin"));
            userService.saveRole(new Role("Role_Super_User"));

            userService.saveUser(new Users(null, "John Troval", "John", "1234", new ArrayList<>()));
            userService.saveUser(new Users(null, "John Troval", "Johnu", "1234", new ArrayList<>()));
            userService.saveUser(new Users(null, "John Troval", "Johna", "1234", new ArrayList<>()));
            userService.saveUser(new Users(null, "John Troval", "Johni", "1234", new ArrayList<>()));

            userService.addRoleToUser("John", "Role_User");
            userService.addRoleToUser("Johnu", "Role_Manager");
            userService.addRoleToUser("Johna", "Role_Admin");
            userService.addRoleToUser("Johni", "Role_Super_User");
        };
    }

}
