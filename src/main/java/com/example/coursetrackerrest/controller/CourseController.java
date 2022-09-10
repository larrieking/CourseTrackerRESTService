package com.example.coursetrackerrest.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.coursetrackerrest.entity.Course;
import com.example.coursetrackerrest.entity.Role;
import com.example.coursetrackerrest.entity.Users;
import com.example.coursetrackerrest.service.ServiceImpl.CourseServiceImpl;
import com.example.coursetrackerrest.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/courses/")
@Tag(name = "Course controller", description = "This REST controller provides services to manage courses in the course tracker application")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private UserServiceImpl userService;
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

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete the course details for the supplied course id from the course tracker application ")
    void deleteCourseById(@PathVariable("id") long courseId) {
        courseService.deleteCourseById(courseId);
    }

    @DeleteMapping("/delete/")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all courses from the course tracker application")
    void deleteCourses() {
        courseService.deleteCourses();
    }

    @GetMapping("token/refresh")
    @Operation(summary = "Entry for refreshing access token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Users user = userService.getUser(username);

                String access_token = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000)).withIssuer(request.getRequestURL().toString()).withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList())).sign(algorithm);
                // String refresh_token = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000)).withIssuer(request.getRequestURL().toString()).sign(algorithm);
                //response.setHeader("access_token", access_token);
                //response.setHeader("refress_token", refresh_token);

                Map<String, String > tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                //String[]roles = decodedJWT.getClaim("roles").asArray(String.class);

                //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                //SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                //filterChain.doFilter(request, response);
            }catch(Exception e){
                //log.error("Error loggin in : {}", e.getMessage());
                response.setHeader("error",  e.getMessage());
                Map<String, String > error = new HashMap<>();
                error.put("access_token", e.getMessage());
                // error.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }


        }
        else{
            throw new  RuntimeException("refresh token is missing");
            //filterChain.doFilter(request,response);
        }

        //userService.addRoleToUser(form.getUsername(), form.getRoleName());
        //return ResponseEntity.ok().build();
    }



}
