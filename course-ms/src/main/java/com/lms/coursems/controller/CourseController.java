package com.lms.coursems.controller;

import com.lms.coursems.exception.CourseNotFoundException;
import com.lms.coursems.exception.InternalServerException;
import com.lms.coursems.exception.ValidationException;
import com.lms.coursems.dto.CourseWrapper;
import com.lms.coursems.dto.ErrorMessage;
import com.lms.coursems.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/add")
    public ResponseEntity addCourse(@RequestBody CourseWrapper coursesWrapper) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(coursesWrapper));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (InternalServerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        } catch (HttpMessageNotReadableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Please input a number for duration."));
        }
    }

    @DeleteMapping(value = "/delete/{courseName}")
    public ResponseEntity deleteCourse(@PathVariable String courseName) {
        try {
            courseService.deleteCourse(courseName);
            return ResponseEntity.ok().build();
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (InternalServerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"));
        }
    }
}
