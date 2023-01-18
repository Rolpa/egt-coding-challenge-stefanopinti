package com.egt.challenge.controller;

import com.egt.challenge.dto.PersonDto;
import com.egt.challenge.dto.PersonMapper;
import com.egt.challenge.model.Person;
import com.egt.challenge.service.PersonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PersonController.BASE_URL)
@RequiredArgsConstructor
public class PersonController {
    public static final String BASE_URL = "/api/persons";

    @NonNull
    private final PersonService personService;
    @NonNull
    private final PersonMapper personMapper;

    @GetMapping("/")
    public ResponseEntity<List<PersonDto>> getAll() {
        List<PersonDto> personList = personService.getAllPersons().stream().map(entity -> personMapper.toDto(entity)).collect(Collectors.toList());

        return ResponseEntity.ok().body(personList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getById(@PathVariable Long id) {
        Optional<Person> personOptional = personService.getSinglePersonById(id);

        if (personOptional.isPresent()) {
            PersonDto personDto = personMapper.toDto(personService.getSinglePersonById(id).get());
            return ResponseEntity.ok().body(personDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PersonDto person) {
        Person newPerson = personMapper.toEntity(person);
        try {
            personService.insertPerson(newPerson);
        }
        catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.created(URI.create("/api/persons/id_goes_here")).body(null);
    }

    @PostMapping("/lastName")
    public ResponseEntity<?> createAndReturnMatchingBySurname(@RequestBody PersonDto person) {
        Person newPerson = personMapper.toEntity(person);
        try {
            personService.insertPerson(newPerson);
        }
        catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        List<PersonDto> personList = personService.getAllPersonsWithLastName(person.getLastName()).stream().map(entity -> personMapper.toDto(entity)).collect(Collectors.toList());

        return ResponseEntity.created(URI.create("/api/persons/id_goes_here")).body(personList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody PersonDto person, @PathVariable Long id) {
        Person newPerson = personMapper.toEntity(person);
        try {
            personService.insertPerson(newPerson);
        }
        catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@RequestBody PersonDto person, @PathVariable Long id) {
        Person newPerson = personMapper.toEntity(person);
        try {
            personService.removePerson(newPerson);
        }
        catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body(null);
    }
}
