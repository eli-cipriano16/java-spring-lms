package com.lms.coursems.dto;

import com.lms.coursems.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEvent {

    private String eventType;

    private Course course;

}
