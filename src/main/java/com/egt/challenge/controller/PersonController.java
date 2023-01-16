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
import java.util.ArrayList;
import java.util.List;
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
        System.out.println(personList);

        return ResponseEntity.ok().body(personList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(new PersonDto());
    }

    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody PersonDto person) {
        Person newPerson = personMapper.toEntity(person);
        personService.insertPerson(newPerson);

        return ResponseEntity.created(URI.create("/api/persons/id_goes_here")).body(new PersonDto());
    }

    @PostMapping("/lastName")
    public ResponseEntity<List<PersonDto>> createAndReturnMatchingBySurname(@RequestBody PersonDto person) {
        ArrayList personList = new ArrayList<>();
        personList.add(new PersonDto());
        personList.add(new PersonDto());
        personList.add(new PersonDto());

        return ResponseEntity.created(URI.create("/api/persons/id_goes_here")).body(personList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updateByID(@RequestBody PersonDto person, @PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonDto> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }
}
