package com.amichdev.spring.rest.services;

import com.amichdev.spring.rest.domains.Author;
import com.amichdev.spring.rest.domains.Course;
import com.amichdev.spring.rest.exceptions.DomainNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    List<Course> courses;

    @PostConstruct
    void init(){

        var a1 = Author.builder()
                .id(1l)
                .name("Author 1")
                .writerId(UUID.randomUUID())
                .build();

        var a2 = Author.builder()
                .id(2l)
                .name("Author 2")
                .writerId(UUID.randomUUID())
                .build();

        courses = new ArrayList<>();

        var c1 = Course.builder()
                .id(1l)
                .name("Course 1")
                .authors(List.of(a1))
                .build();

        var c2 = Course.builder()
                .id(2l)
                .name("Course 2")
                .authors(List.of(a1, a2))
                .build();

        var c3 = Course.builder()
                .id(3l)
                .name("Course 3")
                .authors(List.of(a2))
                .build();

        courses.add(c1);
        courses.add(c2);
        courses.add(c3);

    }

    public Course findByName(String name){
        var c =  courses.stream()
                .filter(course -> course.getName().equalsIgnoreCase(name))
                .findFirst();

        if (c.isEmpty()) {
            throwCourseNotFound("Course not found with name: " + name);
        }
        return c.get();
    }
    public List<Course> findByAuthorName(String name){
        return courses.stream()
                .filter(course -> course.getAuthors().stream()
                        .anyMatch(author -> author.getName().equalsIgnoreCase(name)))
                .collect(Collectors.toList());
    }

    public List<Course> findAll(){
        return courses;
    }

    public boolean save(Course course){
        return courses.add(course);
    }

    public Boolean update(Course course, Long id){

        delete(id);

        // update the course
        course.setId(id);
        courses.add(course);
        return true;
    }

    public Boolean delete(Long id){
        findById(id);

        courses = courses.stream()
                .filter(course1 -> !course1.getId().equals(id))
                .collect(Collectors.toList());
        return true;
    }

    public Course findById(Long id){
        var c = courses.stream()
                .filter(course1 -> course1.getId().equals(id))
                .findFirst();

        if (c.isEmpty()) {
            throwCourseNotFound("Course Id not found: " + id);
        }
        return c.get();
    }

    private void throwCourseNotFound(String msg) {
        throw new DomainNotFoundException(msg);
    }

}
