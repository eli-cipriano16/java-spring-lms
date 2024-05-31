package com.lms.course_query_ms.controller;

import com.lms.course_query_ms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lms/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
}
