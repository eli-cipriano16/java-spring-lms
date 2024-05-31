package com.lms.coursems.model;

import com.lms.coursems.dto.CourseWrapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {

    @Id
    @SequenceGenerator(name="TEACHER_ID_GENERATOR", sequenceName="TEACHER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="TEACHER_ID_GENERATOR")
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
