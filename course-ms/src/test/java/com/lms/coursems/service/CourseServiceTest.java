package com.lms.coursems.service;

import com.lms.coursems.exception.ValidationException;
import com.lms.coursems.model.Course;
import com.lms.coursems.dto.CourseWrapper;
import com.lms.coursems.repository.CourseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository coursesRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private CourseWrapper courseWrapper;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @BeforeEach
    public void init() {
        courseWrapper = new CourseWrapper();
    }

    @Test
    public void testValidateAllFieldsPos() throws ValidationException {
        courseWrapper.setName("Basics of Computer Science and Technology");
        courseWrapper.setDuration(2);
        courseWrapper.setDescription("This course is about the study of computers and computational systems. It spans theoretical " +
                "disciplines (such as algorithms, theory of computation and information theory) to applied disciplines (including the design " +
                "and implementation of hardware and software.");
        courseWrapper.setTechnology("Computer Science");
        courseWrapper.setLaunchUrl("www.compsci.com");
        testValidateAllFieldsPositiveScenario(courseWrapper);
    }

    public void testValidateAllFieldsNeg() throws ValidationException {
        courseWrapper.setName("Comp Sci");
        courseWrapper.setDuration(0);
        courseWrapper.setDescription("This course is about the study of computers.");
        courseWrapper.setTechnology(null);
        courseWrapper.setLaunchUrl(null);
        testValidateAllFieldsNegativeScenario(courseWrapper);
    }

    @Test
    public void testValidateAllFieldsNeg2() throws ValidationException {
        courseWrapper.setName(null);
        courseWrapper.setDuration(null);
        courseWrapper.setDescription(null);
        courseWrapper.setTechnology("Computer Science");
        courseWrapper.setLaunchUrl("www.compsci.com");
        testValidateAllFieldsNegativeScenario(courseWrapper);
    }

    @Test
    public void testValidateAllFieldsNeg3() throws ValidationException {
        courseWrapper.setName("");
        courseWrapper.setDuration(1);
        courseWrapper.setDescription("");
        courseWrapper.setTechnology("");
        courseWrapper.setLaunchUrl("");
        testValidateAllFieldsNegativeScenario(courseWrapper);
    }

    @Test
    public void testSaveUser() throws ValidationException{
        Course courses = new Course();
        CourseWrapper courseWrapper = new CourseWrapper("Basics of Computer Science and Technology",
                2, "This course is about the study of computers and computational systems. It spans theoretical " +
                "disciplines (such as algorithms, theory of computation and information theory) to applied disciplines (including the design " +
                "and implementation of hardware and software.",
                "Computer Science",
                "www.compsci.com");
        courses.setName(courseWrapper.getName());
        courses.setDuration(courseWrapper.getDuration());
        courses.setDescription(courseWrapper.getDescription());
        courses.setTechnology(courseWrapper.getTechnology());
        courses.setLaunchUrl(courseWrapper.getLaunchUrl());
        when(coursesRepository.save(Mockito.any(Course.class))).thenReturn(courses);
        CourseWrapper savedCourse = courseService.saveCourse(courseWrapper);
        Assertions.assertThat(savedCourse).isNotNull();
    }

    @Test
    public void testDeleteCourse() {
        String courseName = "Basics of Computer Science and Technology";
        String description = "This course is about the study of computers and computational systems. It spans theoretical " +
                "disciplines (such as algorithms, theory of computation and information theory) to applied disciplines (including the design " +
                "and implementation of hardware and software.";
        String technology = "Computer Science";
        String launchUrl = "www.compsci.com";
        Course course = new Course(courseName, 1, description, technology, launchUrl);

        when(coursesRepository.findByNameAndDel(courseName, 0)).thenReturn(course);
        when(coursesRepository.save(course)).thenReturn(course);

        courseService.deleteCourse(courseName);
        assertAll(() -> courseService.deleteCourse(courseName));
    }

    private void testValidateAllFieldsPositiveScenario(CourseWrapper requestBody) throws ValidationException {
        assertDoesNotThrow(() -> courseService.validateAllFields(requestBody));
    }

    private void testValidateAllFieldsNegativeScenario(CourseWrapper requestBody) throws ValidationException {
        assertThrows(ValidationException.class, () -> courseService.validateAllFields(requestBody));
    }

}
