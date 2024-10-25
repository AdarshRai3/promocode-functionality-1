package com.bootcodingTask.promocode_functionality.promocode_functionality.repository;

import com.bootcodingTask.promocode_functionality.promocode_functionality.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses,Long> {
    List<Courses> findByCourseName(String courseName);
}
