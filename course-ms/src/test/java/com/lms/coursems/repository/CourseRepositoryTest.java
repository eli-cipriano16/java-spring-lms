package com.lms.coursems.repository;

import com.lms.coursems.model.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void testCreateCourse() {
        String courseName = "Basics of Computer Science and Technology";
        String description = "This course is about the study of computers and computational systems. It spans theoretical " +
                "disciplines (such as algorithms, theory of computation and information theory) to applied disciplines (including the design " +
                "and implementation of hardware and software.";
        String technology = "Computer Science";
        String launchUrl = "www.compsci.com";
        Course course = new Course(courseName, 1, description, technology, launchUrl);

        Course savedCourse = courseRepository.save(course);

        Assertions.assertThat(savedCourse).isNotNull();
        Assertions.assertThat(savedCourse.getId()).isNotNull();
    }
}
