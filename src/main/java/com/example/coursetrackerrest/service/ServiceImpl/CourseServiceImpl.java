package com.example.coursetrackerrest.service.ServiceImpl;

import com.example.coursetrackerrest.entity.Course;
import com.example.coursetrackerrest.exception.CourseNotFoundexception;
import com.example.coursetrackerrest.repository.CourseRepository;
import com.example.coursetrackerrest.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> getCourseById(long courseId) {
        Optional<Course> course = Optional.ofNullable(courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundexception(String.format("No course with id %s is available", courseId))));
        return course;
    }

    @Override
    public Iterable<Course> getCoursesByCategory(String category) {
        return courseRepository.findAllByCategory(category);
    }

    @Override
    public Iterable<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void updateCourse(long courseId, Course course) {
        Course dbCourse = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundexception(String.format("no course with id %s", courseId)));
        dbCourse.setName(course.getName());
        dbCourse.setCategory(course.getCategory());
        dbCourse.setRating(course.getRating());
        dbCourse.setDescription(course.getDescription());
        courseRepository.save(dbCourse);
    }




    @Override
    public void deleteCourseById(long courseId) {
    courseRepository.findById(courseId).orElseThrow(()->new CourseNotFoundexception(String.format("Course with course id %s not found", courseId)));
        courseRepository.deleteById(courseId);

    }

    @Override
    public void deleteCourses() {
        courseRepository.deleteAll();

    }
}
