package com.lms.coursems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.coursems.dto.CourseWrapper;
import com.lms.coursems.exception.CourseNotFoundException;
import com.lms.coursems.model.Course;
import com.lms.coursems.service.CourseService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourseController.class)
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService coursesService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseWrapper coursesWrapper;

    private Course courses;

    private List<CourseWrapper> courseList;

    @BeforeEach
    public void init() {
        coursesWrapper = new CourseWrapper();
        courses = new Course();
        courseList = new ArrayList<>();
        courseList.add(Mockito.mock(CourseWrapper.class));
    }

    @Test
    public void testAddCourse () throws Exception {
        given(coursesService.saveCourse(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/lms/courses/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursesWrapper)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(coursesWrapper.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration", CoreMatchers.is(coursesWrapper.getDuration())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(coursesWrapper.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.technology", CoreMatchers.is(coursesWrapper.getTechnology())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.launchUrl", CoreMatchers.is(coursesWrapper.getLaunchUrl())));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        String courseName = "Computer Science and Technology";

        doNothing().when(coursesService).deleteCourse(courseName);

        ResultActions response = mockMvc.perform(delete("/lms/courses/delete/" + courseName)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void testDeleteCourseNotFound() throws Exception {
        String courseName = "IT";

        doThrow(new CourseNotFoundException("Course not found")).when(coursesService).deleteCourse(courseName);

        ResultActions response = mockMvc.perform(delete("/lms/courses/delete/" + courseName)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }
}
