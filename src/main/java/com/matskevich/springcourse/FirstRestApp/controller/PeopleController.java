package com.matskevich.springcourse.FirstRestApp.controller;

import com.matskevich.springcourse.FirstRestApp.dto.PersonDTO;
import com.matskevich.springcourse.FirstRestApp.models.Person;
import com.matskevich.springcourse.FirstRestApp.services.PeopleService;
import com.matskevich.springcourse.FirstRestApp.util.PersonErrorResponse;
import com.matskevich.springcourse.FirstRestApp.util.PersonNotCreatedException;
import com.matskevich.springcourse.FirstRestApp.util.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

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

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult) { // or  public Person create(...)
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.save(convertToPerson(personDTO));

        //sent HTTp response with empty body, status 200
        return ResponseEntity.ok(HttpStatus.OK);
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

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        // in HTTP response: response body and status in title
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);    // BAD_REQUEST - 400 Status
    }

    private Person convertToPerson(PersonDTO personDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(personDTO, Person.class);
    }
}
