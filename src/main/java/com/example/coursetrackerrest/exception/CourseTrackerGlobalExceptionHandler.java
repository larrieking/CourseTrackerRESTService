package com.example.coursetrackerrest.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CourseTrackerGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CourseNotFoundexception.class})
    public ResponseEntity<?>handleCourseNotFound(CourseNotFoundexception courseNotFoundexception, WebRequest webRequest){
        return super.handleExceptionInternal(courseNotFoundexception, courseNotFoundexception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }
}
