package com.amichdev.spring.rest.controllers;

import com.amichdev.spring.rest.domains.Course;
import com.amichdev.spring.rest.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/webflux/courses")
@AllArgsConstructor
public class CoursesWebFluxController {

    final CourseService courseService;

    private void subscribeFlux(Flux<Course> fluxResult) {
        fluxResult.subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Task completed"));
    }

    private void subscribeMono(Mono<Course> monoResult) {
        monoResult.subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Task completed"));
    }

    @GetMapping
    public Flux<Course> findAll(){
        var fluxResult = Flux.fromIterable(courseService.findAll());
        subscribeFlux(fluxResult);
        return fluxResult;
    }

    @GetMapping("/find/author/{authorName}")
    public Flux<Course> findByAuthor(@PathVariable @NonNull String authorName){

        var fluxResult = Flux.fromIterable(courseService.findByAuthorName(authorName));
        subscribeFlux(fluxResult);
        return fluxResult;
    }

    @GetMapping("/find")
    public Mono<Course> findByName(@RequestParam @NonNull  String name){
        var mono = Mono.justOrEmpty(courseService.findByName(name));
        subscribeMono(mono);
        return  mono;

    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED )
    public Mono<Boolean> save(@RequestBody Course course){
        return Mono.just(courseService.save(course));

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
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> delete(long id) {
        // returns nothing , thats why calling fromRunnable
        return Mono.fromRunnable(() -> courseService.delete(id));
    }
}
