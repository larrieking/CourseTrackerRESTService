package com.example.coursetrackerrest;

import com.example.coursetrackerrest.entity.Course;
import com.example.coursetrackerrest.service.ServiceImpl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseTrackerRestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CourseTrackerRestApplication.class, args);
    }

    @Autowired
    private CourseServiceImpl courseService;
    @Override
    public void run(String... args) throws Exception {

        Course course = new Course();
        course.setDescription("A newly published book");
        course.setCategory("Java");
        course.setRating(5);
        course.setName("Java Book");
        courseService.createCourse(course);
        System.out.println("Success");

    }
}
