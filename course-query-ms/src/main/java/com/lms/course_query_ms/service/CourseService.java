package com.lms.course_query_ms.service;

import com.lms.course_query_ms.dto.CourseSearchResults;
import com.lms.course_query_ms.exception.ValidationException;

public interface CourseService {

    CourseSearchResults getAllCourses();

    CourseSearchResults getAllCoursesByTech(String technology) throws ValidationException;

    CourseSearchResults getAllCoursesByDurationRangeAndTech(String technology, Integer durationFrom, Integer durationTo) throws ValidationException;

}
