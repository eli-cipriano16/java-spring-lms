package com.lms.coursems.model;

import com.lms.coursems.dto.CourseWrapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="course")
public class Course {

    @Id
    @SequenceGenerator(name="COURSE_ID_GENERATOR", sequenceName="COURSE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="COURSE_ID_GENERATOR")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "description")
    private String description;

    @Column(name = "technology")
    private String technology;

    @Column(name = "launch_url")
    private String launchUrl;

    @Column(name = "del")
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
