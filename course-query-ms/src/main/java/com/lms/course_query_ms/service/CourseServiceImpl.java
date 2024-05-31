package com.lms.course_query_ms.service;

import com.lms.course_query_ms.dto.CourseEvent;
import com.lms.course_query_ms.dto.CourseSearchResults;
import com.lms.course_query_ms.exception.ValidationException;
import com.lms.course_query_ms.model.Course;
import com.lms.course_query_ms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private String validateTechnology(String technology) {
        String errorMsg = "";

        if(technology == null || technology.isEmpty() || technology.isBlank()) {
            errorMsg = "Technology is required. ";
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

    @Override
    public CourseSearchResults getAllCourses() {
        List<Course> courses = courseRepository.findByDel(0);
        if(courses != null) {
            return new CourseSearchResults(courses.stream().map(Course::getDto).collect(Collectors.toList()));
        } else {
            return new CourseSearchResults();
        }
    }

    @Override
    public CourseSearchResults getAllCoursesByTech(String technology) throws ValidationException {
        String errorMsg = validateTechnology(technology);
        if(!errorMsg.isEmpty()){
            throw new ValidationException(errorMsg);
        }
        List<Course> courses = courseRepository.findByTechnologyAndDel(technology, 0);
        if(courses != null) {
            return new CourseSearchResults(courses.stream().map(Course::getDto).collect(Collectors.toList()));
        } else {
            return new CourseSearchResults();
        }
    }

    @Override
    public CourseSearchResults getAllCoursesByDurationRangeAndTech(String technology, Integer durationFrom, Integer durationTo) throws ValidationException {
        String errorMsg = validateTechnology(technology);
        errorMsg += validateDuration(durationFrom);
        errorMsg += validateDuration(durationTo);
        if (!errorMsg.isEmpty()) {
            throw new ValidationException(errorMsg);
        }
        List<Course> courses = courseRepository.findDurationRangeAndTechnology(durationFrom, durationTo, 0, technology);

        if (courses != null) {
            return new CourseSearchResults(courses.stream().map(Course::getDto).collect(Collectors.toList()));
        } else {
            return new CourseSearchResults();
        }
    }

    @KafkaListener(topics="course-event-topic", groupId = "course-event-group")
    public void processEvents(CourseEvent event) {
        Course course = event.getCourse();
        if(event.getEventType().equals("CreateCourse")) {
            courseRepository.save(course);
        }else if(event.getEventType().equals("DeleteCourse")) {
            Course existingCourse = courseRepository.findById(course.getId()).get();
            existingCourse.setDel(1);
            courseRepository.save(existingCourse);
        }
    }

}
