package com.egt.challenge.service;

import com.egt.challenge.model.Person;
import com.egt.challenge.repo.PersonRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    @NonNull
    private final PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        List<Person> allPersons = personRepository.findAll();

        Comparator<Person> comparator = Comparator.comparing(person -> person.getLastName());
        comparator = comparator.thenComparing(Comparator.comparing(person -> person.getFirstName()));

        return allPersons.stream().sorted(comparator).toList();
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
    public Person insertPerson(Person person) throws UnsupportedOperationException {

        if(person.getId() != null) {
            throw new UnsupportedOperationException();
        }

        personRepository.save(person);

        return person;

    }

    @Override
    public Person updatePerson(Person person) throws UnsupportedOperationException {
        if(person.getId() == null) {
            throw new UnsupportedOperationException("Cannot update a person without an ID.");
        }
        else if (person.getMainAddress() == null) {
            throw new UnsupportedOperationException("Cannot update a person to have a null main address.");
        }

        Optional<Person> oldRecordOptional = personRepository.findById(person.getId());

        if (oldRecordOptional.isPresent()) {
            Person oldRecord = oldRecordOptional.get();

            if (oldRecord.getBirthDate().compareTo(person.getBirthDate()) != 0) {
                throw new UnsupportedOperationException("Cannot update a person to have a different birthday.");
            }

            personRepository.save(person);
            return person;
        }
        else {
            throw new UnsupportedOperationException("The person with id (" + person.getId() + ") could not be found.");
        }

    }

    @Override
    public void removePerson(Person person) throws UnsupportedOperationException {
        personRepository.delete(person);
    }

}
