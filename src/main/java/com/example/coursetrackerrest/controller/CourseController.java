package com.example.coursetrackerrest.controller;


import com.example.coursetrackerrest.entity.Course;
import com.example.coursetrackerrest.service.ServiceImpl.CourseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/courses/")
@Tag(name = "Course controller", description = "This REST controller provides services to manage courses in the course tracker application")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "provides all courses available in the course tracker application")
    public Iterable<Course>getAllCourses(){
        return courseService.getCourses();
    }

    @GetMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Provides course details for the supplied course id from the course tracker application")
    public Optional<Course> getCourseById(@PathVariable("id") long courseId) {
        return courseService.getCourseById(courseId);
    }

    @GetMapping("category/{name}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Provides course details for the supplied course category from the course tracker application")

    public Iterable<Course> getCourseByCategory(@PathVariable("name")
String category) {
        return courseService.getCoursesByCategory(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create new course in the course tracker application")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "update the course details in the course tracker application for the supplied course ID")
    public void updateCourse(@PathVariable("id") long courseId,
 @RequestBody Course course) {
        courseService.updateCourse(courseId, course);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete the course details for the supplied course id from the course tracker application ")
    void deleteCourseById(@PathVariable("id") long courseId) {
        courseService.deleteCourseById(courseId);
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all courses from the course tracker application")
    void deleteCourses() {
        courseService.deleteCourses();
    }


}
