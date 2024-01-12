package com.amichdev.spring.rest.controllers;


import com.amichdev.spring.rest.domains.Course;
import com.amichdev.spring.rest.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Courses", description = "Courses Api using Spring WebServices")
@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CoursesController {

    final CourseService courseService;


    @Operation(
            summary = "Fetch all courses",
            description = "fetches all courses entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping
    public ResponseEntity findAll(){
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/find/author/{authorName}")
    public ResponseEntity<List> findByAuthor(@PathVariable @NonNull String authorName){
        return ResponseEntity.ok(courseService.findByAuthorName(authorName));
    }

    @Operation(
            summary = "Find course by name",
            description = "Find course by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found course"),
            @ApiResponse(responseCode = "404", description = "Not found any course")
    })
    @GetMapping("/find")
    public ResponseEntity<Course> findByName(@RequestParam @NonNull  String name){
        return ResponseEntity.ok(courseService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody Course course){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    /*
    @PatchMapping("update/partial")
    public ResponseEntity<Course> updatePartially(@RequestBody Course course){
        return ResponseEntity.ok(course);
    }*/

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateCourse(@RequestBody Course course, @PathVariable Long id){
        return ResponseEntity.ok(courseService.update(course, id));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(long id){
        return ResponseEntity.ok(courseService.delete(id));
    }
}
