package com.lms.coursems.service;

import com.lms.coursems.dto.CourseEvent;
import com.lms.coursems.exception.CourseNotFoundException;
import com.lms.coursems.exception.ValidationException;
import com.lms.coursems.model.Course;
import com.lms.coursems.dto.CourseWrapper;
import com.lms.coursems.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private String validateName(String name) {
        String errorMsg = "";
        if(name == null || name.isEmpty() || name.isBlank()) {
            errorMsg = "Course Name is required. ";
            if(name == null) {
                return errorMsg;
            }
        }

        if(name.length() < 20) {
            errorMsg = "Minimum length of Course Name should be at least 20 characters. ";
        }

        Course existingCourse = courseRepository.findByNameAndDel(name, 0);

        if(existingCourse != null) {
            errorMsg = "Please try a different course name. ";
        }

        return errorMsg;
    }

    private String validateDuration(Integer duration) {
        String errorMsg = "";

        if(duration == null) {
            errorMsg = "Course Duration is required. ";
        }

        return errorMsg;
    }

    private String validateDescription(String description) {
        String errorMsg = "";
        if(description == null || description.isEmpty() || description.isBlank()) {
            errorMsg = "Course Description is required. ";
            if(description == null) {
                return errorMsg;
            }
        }

        if(description.length() < 100) {
            errorMsg = "Minimum of length of Course Description should be at least 100 characters. ";
        }

        return errorMsg;
    }

    private String validateTechnology(String technology) {
        String errorMsg = "";

        if(technology == null || technology.isEmpty() || technology.isBlank()) {
            errorMsg = "Technology is required. ";
        }

        return errorMsg;
    }

    private String validateUrl(String url) {
        String errorMsg = "";

        if(url == null || url.isEmpty() || url.isBlank()) {
            errorMsg = "Launch URL is required. ";
        }

        return errorMsg;
    }

    @Override
    public void validateAllFields(CourseWrapper coursesWrapper) throws ValidationException {
        StringBuilder errorMsgSummary = new StringBuilder();
        errorMsgSummary.append(validateName(coursesWrapper.getName()));
        errorMsgSummary.append(validateDuration(coursesWrapper.getDuration()));
        errorMsgSummary.append(validateDescription(coursesWrapper.getDescription()));
        errorMsgSummary.append(validateTechnology(coursesWrapper.getTechnology()));
        errorMsgSummary.append(validateUrl(coursesWrapper.getLaunchUrl()));
        if(errorMsgSummary != null && errorMsgSummary.length() > 0) {
            throw new ValidationException(errorMsgSummary.toString());
        }
    }

    @Override
    public CourseWrapper saveCourse(CourseWrapper requestBody) throws ValidationException {
        validateAllFields(requestBody);
        Course course = new Course();
        course.setName(requestBody.getName());
        course.setDuration(requestBody.getDuration());
        course.setDescription(requestBody.getDescription());
        course.setTechnology(requestBody.getTechnology());
        course.setLaunchUrl(requestBody.getLaunchUrl());
        course.setCreatedDate(new Date());
        Course courseSaved = courseRepository.save(course);
        CourseEvent event = new CourseEvent("CreateCourse", courseSaved);
        kafkaTemplate.send("course-event-topic", event);

        return requestBody;
    }

    @Override
    public void deleteCourse(String courseName) throws CourseNotFoundException {
        Course course = courseRepository.findByNameAndDel(courseName, 0);
        if(course != null) {
            course.setDel(1);
            Course deletedCourse = courseRepository.save(course);
            CourseEvent event = new CourseEvent("DeleteCourse", deletedCourse);
            kafkaTemplate.send("course-event-topic", event);
        } else {
            throw new CourseNotFoundException("Course " + courseName + " not found.");
        }
    }
}
