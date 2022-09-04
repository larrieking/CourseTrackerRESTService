package com.example.coursetrackerrest.repository;

import com.example.coursetrackerrest.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Iterable<Course> findAllByCategory(String category);
}
