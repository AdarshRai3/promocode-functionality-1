package com.bootcodingTask.promocode_functionality.promocode_functionality.controllers;

import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.reponse.CoursesResponse;
import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.request.CoursesRequests;
import com.bootcodingTask.promocode_functionality.promocode_functionality.services.CoursesServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("api/admin/courses")
public class CoursesController {

    @Autowired
    private CoursesServices coursesServices;

    @PostMapping
    public ResponseEntity<CoursesResponse> createCourses(@Valid @RequestBody  CoursesRequests coursesRequests){
         CoursesResponse coursesResponse = coursesServices.saveCourses(coursesRequests);
         return ResponseEntity.ok(coursesResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CoursesResponse> getCourseById(@PathVariable("id") Long courseId) {
        return ResponseEntity.ok(coursesServices.getCourseById(courseId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CoursesResponse> updateCourse(@PathVariable("id") Long courseId, @RequestBody CoursesRequests coursesRequests) {
        return ResponseEntity.ok(coursesServices.updateCourse(courseId, coursesRequests));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long courseId) {
        coursesServices.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public ResponseEntity<List<CoursesResponse>> searchCourses(@RequestParam String name) {
        return ResponseEntity.ok(coursesServices.searchCoursesByName(name));
    }

    @GetMapping
    public ResponseEntity<Page<CoursesResponse>> getAllCourses(Pageable pageable) {
        return ResponseEntity.ok(coursesServices.getAllCourses(pageable));
    }
}
