package com.example.coursetrackerrest;

import com.example.coursetrackerrest.entity.Course;
import com.example.coursetrackerrest.service.ServiceImpl.CourseServiceImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseTrackerRestApplication implements CommandLineRunner {



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
    @Override
    public void run(String... args) throws Exception {
        Course course = Course.builder()
                .name("Java")
                .description("A new Java Book")
                .rating(5)
                .category("Java")
                .build();

        courseService.createCourse(course);
        System.out.println("Success");



    }


}
