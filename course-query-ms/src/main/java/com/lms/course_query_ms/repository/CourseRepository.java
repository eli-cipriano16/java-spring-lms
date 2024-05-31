package com.lms.course_query_ms.repository;

import com.lms.course_query_ms.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findByTechnologyAndDel(String technology, Integer del);

    List<Course> findByDel(Integer del);

    @Query("{$and: [{'duration': {'$gte': ?0}}, {'duration': {'$lte': ?1}}, {'del': ?2}, {'technology': ?3}]}")
    List<Course> findDurationRangeAndTechnology(Integer durationFrom, Integer durationTo, Integer del, String technology);
}
