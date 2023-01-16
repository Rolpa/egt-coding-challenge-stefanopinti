package com.egt.challenge.service;

import com.egt.challenge.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    public List<Person> getAllPersons();

    public List<Person> getAllPersonsWithLastName(String lastName);

    public Optional<Person> getSinglePersonById(Long id);

    public void insertPerson(Person person) throws UnsupportedOperationException;

    public void updatePerson(Person person) throws UnsupportedOperationException;

    public void removePerson(Person person) throws UnsupportedOperationException;
}
