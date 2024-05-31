package com.lms.course_query_ms.model;

import com.lms.course_query_ms.dto.CourseWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection= "course")
public class Course {

    @Id
    private String id;
    private String name;
    private Integer duration;
    private String description;
    private String technology;
    private String launchUrl;
    private Date createdDate;
    private Integer del = 0;

    public Course(String name, Integer duration,
                   String description, String technology,
                   String launchUrl) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.technology = technology;
        this.launchUrl = launchUrl;
    }

    public CourseWrapper getDto(){
        CourseWrapper coursesWrapper = new CourseWrapper();
        coursesWrapper.setDescription(getDescription());
        coursesWrapper.setDuration(getDuration());
        coursesWrapper.setName(getName());
        coursesWrapper.setTechnology(getTechnology());
        coursesWrapper.setLaunchUrl(getLaunchUrl());
        return coursesWrapper;
    }

}
