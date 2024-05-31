package com.lms.coursems.service;

import com.lms.coursems.exception.CourseNotFoundException;
import com.lms.coursems.exception.ValidationException;
import com.lms.coursems.dto.CourseWrapper;

public interface CourseService {

    void validateAllFields(CourseWrapper coursesWrapper) throws ValidationException;

    CourseWrapper saveCourse(CourseWrapper coursesWrapper) throws ValidationException;

    void deleteCourse(String courseName) throws CourseNotFoundException;

}
