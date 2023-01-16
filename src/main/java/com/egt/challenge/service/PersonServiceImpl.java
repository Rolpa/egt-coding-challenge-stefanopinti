package com.egt.challenge.service;

import com.egt.challenge.model.Person;
import com.egt.challenge.repo.PersonRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    @NonNull
    private final PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> getAllPersonsWithLastName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    @Override
    public Optional<Person> getSinglePersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public void insertPerson(Person person) throws UnsupportedOperationException {

        if(person.getId() != null) {
            throw new UnsupportedOperationException();
        }

        personRepository.save(person);

    }

    @Override
    public void updatePerson(Person person) throws UnsupportedOperationException {

    }

    @Override
    public void removePerson(Person person) throws UnsupportedOperationException {

    }

}
