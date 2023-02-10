package com.matskevich.springcourse.FirstRestApp.controller;

import com.matskevich.springcourse.FirstRestApp.models.Person;
import com.matskevich.springcourse.FirstRestApp.repositories.PeopleRepository;
import com.matskevich.springcourse.FirstRestApp.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
