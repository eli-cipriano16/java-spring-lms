package com.lms.course_query_ms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.course_query_ms.dto.CourseSearchResults;
import com.lms.course_query_ms.dto.CourseWrapper;
import com.lms.course_query_ms.model.Course;
import com.lms.course_query_ms.service.CourseService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private CourseSearchResults coursesSearchResults;

    private Course course;

    private List<CourseWrapper> courseList;

    @BeforeEach
    public void init() {
        coursesWrapper = new CourseWrapper();
        coursesSearchResults = new CourseSearchResults();
        course = new Course();
        courseList = new ArrayList<>();
        courseList.add(Mockito.mock(CourseWrapper.class));
    }

    @Test
    public void testGetCoursesByTechnology() throws Exception {
        String technology = "Engineering";
        coursesSearchResults.setCourseList(courseList);
        when(coursesService.getAllCoursesByTech(technology)).thenReturn(coursesSearchResults);

        ResultActions response = mockMvc.perform(get("/info/Engineering")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursesSearchResults)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.courseList.size()", CoreMatchers.is(coursesSearchResults.getCourseList().size())));

    }

    @Test
    public void testGetCoursesByTechEmpty() throws Exception {
        String technology = "Engineering";
        coursesSearchResults.setCourseList(new ArrayList<>());
        when(coursesService.getAllCoursesByTech(technology)).thenReturn(coursesSearchResults);

        ResultActions response = mockMvc.perform(get("/info/Engineering")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursesSearchResults)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.courseList.size()", CoreMatchers.is(0)));
    }

    @Test
    public void testGetAllCourses() throws Exception {
        coursesSearchResults.setCourseList(courseList);
        when(coursesService.getAllCourses()).thenReturn(coursesSearchResults);

        ResultActions response = mockMvc.perform(get("/getall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursesSearchResults)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.courseList.size()", CoreMatchers.is(coursesSearchResults.getCourseList().size())));
    }

    @Test
    public void testGetAllCoursesEmpty() throws Exception {
        coursesSearchResults.setCourseList(new ArrayList<>());
        when(coursesService.getAllCourses()).thenReturn(coursesSearchResults);

        ResultActions response = mockMvc.perform(get("/getall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursesSearchResults)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.courseList.size()", CoreMatchers.is(0)));
    }

    @Test
    public void testGetAllCoursesByDurationAndTech() throws Exception {
        String technology = "Engineering";
        Integer durationFrom = 1;
        Integer durationTo = 3;
        coursesSearchResults.setCourseList(courseList);
        when(coursesService.getAllCoursesByDurationRangeAndTech(technology, durationFrom, durationTo)).thenReturn(coursesSearchResults);

        ResultActions response = mockMvc.perform(get("/get/Engineering/1/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursesSearchResults)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.courseList.size()", CoreMatchers.is(coursesSearchResults.getCourseList().size())));
    }

    @Test
    public void testGetAllCoursesByDurationAndTechEmpty() throws Exception {
        String technology = "IT";
        Integer durationFrom = 1;
        Integer durationTo = 3;
        coursesSearchResults.setCourseList(new ArrayList<>());
        when(coursesService.getAllCoursesByDurationRangeAndTech(technology, durationFrom, durationTo)).thenReturn(coursesSearchResults);

        ResultActions response = mockMvc.perform(get("/get/IT/1/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursesSearchResults)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.courseList.size()", CoreMatchers.is(0)));
    }
}
