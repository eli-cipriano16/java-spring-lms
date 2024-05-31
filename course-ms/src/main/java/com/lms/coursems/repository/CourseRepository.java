package com.lms.coursems.repository;

import com.lms.coursems.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findByNameAndDel(String courseName, Integer del);

}
