package com.lms.course_query_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseWrapper {

    private String name;
    private Integer duration;
    private String description;
    private String technology;
    private String launchUrl;

}
