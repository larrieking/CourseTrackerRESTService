package com.example.coursetrackerrest;
import com.jayway.jsonpath.JsonPath;
import com.example.coursetrackerrest.entity.Course;
import com.example.coursetrackerrest.service.ServiceImpl.CourseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
class CourseTrackerRestApplicationTests {

    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void textPostCourse() throws Exception {
        Course course = Course.builder().name("Rapid Spring Boot Application").category("Spring")
                        .rating(5).description("Rapid Sprin Application DDevelopment").build();


        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = mockMvc.perform(post("/courses/")
                .contentType("applicattion/json")
                .content(objectMapper.writeValueAsString(course)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Rapid Spring Boot Application"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(status().isCreated()).andReturn().getResponse();

        Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");
        assertNotNull(courseService.getCourseById(id));




    }

}
