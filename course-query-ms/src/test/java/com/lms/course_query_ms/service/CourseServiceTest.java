package com.lms.course_query_ms.service;

import com.lms.course_query_ms.dto.CourseSearchResults;
import com.lms.course_query_ms.dto.CourseWrapper;
import com.lms.course_query_ms.model.Course;
import com.lms.course_query_ms.repository.CourseRepository;
import org.apache.http.HttpException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository coursesRepository;

    @InjectMocks
    private CourseServiceImpl coursesService;

    private CourseWrapper coursesWrapper;

    public CourseServiceTest() {
    }

    @BeforeEach
    public void init() {
        coursesWrapper = new CourseWrapper();
    }

    public void testGetAllCourses() {
        List<Course> coursesList = Mockito.mock(List.class);
        when(coursesRepository.findByDel(0)).thenReturn(coursesList);
        CourseSearchResults searchResults = coursesService.getAllCourses();

        Assertions.assertThat(searchResults.getCourseList()).isNotNull();
    }

    @Test
    public void testGetAllCoursesEmpty() {
        when(coursesRepository.findByDel(0)).thenReturn(null);
        CourseSearchResults searchResults = coursesService.getAllCourses();

        Assertions.assertThat(searchResults.getCourseList()).isNull();
    }

    @Test
    public void testGetAllCoursesByTech() throws HttpException {
        String technology = "Engineering";
        List<Course> coursesList = Mockito.mock(List.class);
        when(coursesRepository.findByTechnologyAndDel(technology, 0)).thenReturn(coursesList);
        CourseSearchResults searchResults = coursesService.getAllCoursesByTech(technology);

        Assertions.assertThat(searchResults.getCourseList()).isNotNull();
    }

    @Test
    public void testGetAllCoursesByTechEmpty() throws HttpException {
        String technology = "IT";
        List<Course> coursesList = null;
        when(coursesRepository.findByTechnologyAndDel(technology, 0)).thenReturn(coursesList);
        CourseSearchResults searchResults = coursesService.getAllCoursesByTech(technology);

        Assertions.assertThat(searchResults.getCourseList()).isNull();
    }

    @Test
    public void testGetAllCoursesByDurationRangeAndTech() throws HttpException {
        String technology = "Engineering";
        Integer durationFrom = 1;
        Integer durationTo = 3;
        List<Course> coursesList = Mockito.mock(List.class);
        when(coursesRepository.findDurationRangeAndTechnology(durationFrom, durationTo, 0, technology)).thenReturn(coursesList);
        CourseSearchResults searchResults = coursesService.getAllCoursesByDurationRangeAndTech(technology, durationFrom, durationTo);

        Assertions.assertThat(searchResults.getCourseList()).isNotNull();
    }

    @Test
    public void testGetAllCoursesByDurationRangeAndTechEmpty() throws HttpException {
        String technology = "IT";
        Integer durationFrom = 1;
        Integer durationTo = 3;
        List<Course> coursesList = null;
        when(coursesRepository.findDurationRangeAndTechnology(durationFrom, durationTo, 0, technology)).thenReturn(coursesList);
        CourseSearchResults searchResults = coursesService.getAllCoursesByDurationRangeAndTech(technology, durationFrom, durationTo);

        Assertions.assertThat(searchResults.getCourseList()).isNull();
    }
}
