package com.example.coursetrackerrest.exception;

public class CourseNotFoundexception extends RuntimeException{

    private static final long serialVersionUID = 5071646428281007896L;

    public CourseNotFoundexception(String message) {
        super(message);
    }
}
