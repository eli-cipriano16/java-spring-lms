package com.lms.course_query_ms.controller;

import com.lms.course_query_ms.dto.ErrorMessage;
import com.lms.course_query_ms.exception.InternalServerException;
import com.lms.course_query_ms.exception.ValidationException;
import com.lms.course_query_ms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/info/{technology}")
    public ResponseEntity getCoursesByTechnology(@PathVariable String technology) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCoursesByTech(technology));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (InternalServerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"));
        }
    }

    @GetMapping(value = "/getall")
    public ResponseEntity getAllCourses() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourses());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping(value = "/get/{technology}/{durationFromRange}/{durationToRange}")
    public ResponseEntity getAllCoursesByDurationAndTech(
            @PathVariable String technology,
            @PathVariable Integer durationFromRange,
            @PathVariable Integer durationToRange
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCoursesByDurationRangeAndTech(technology, durationFromRange, durationToRange));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (InternalServerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"));
        } catch (MethodArgumentTypeMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Please input a number for duration fields."));
        }
    }
}
