package com.lms.course_query_ms.dto;

import com.lms.course_query_ms.model.Course;
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
