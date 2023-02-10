package com.matskevich.springcourse.FirstRestApp.controller;

import com.matskevich.springcourse.FirstRestApp.models.Person;
import com.matskevich.springcourse.FirstRestApp.repositories.PeopleRepository;
import com.matskevich.springcourse.FirstRestApp.services.PeopleService;
import com.matskevich.springcourse.FirstRestApp.util.PersonErrorResponse;
import com.matskevich.springcourse.FirstRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> getPeople() {
        return peopleService.findAll(); //Jackson convert objects->JSON
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "person with this id wasn't found!",
                System.currentTimeMillis()
        );

        // in HTTP response: response body and status in title
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // NOT_FOUND - 404 Status
    }
}
