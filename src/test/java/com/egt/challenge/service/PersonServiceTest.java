package com.egt.challenge.service;

import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;
import com.egt.challenge.model.Person;
import com.egt.challenge.repo.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person;

    private Person personNoId;

    @BeforeEach
    public void setup(){

        person = Person.builder()
                .id(1L)
                .firstName("Stefano")
                .lastName("Pinti")
                .birthDate(LocalDate.of(1993, 1, 4))
                .mainAddress(new Address())
                .additionalAddresses(new HashSet<Address>())
                .build();

        personNoId = Person.builder()
                .id(1L)
                .firstName("Stefano")
                .lastName("Pinti")
                .birthDate(LocalDate.of(1993, 1, 4))
                .mainAddress(new Address())
                .additionalAddresses(new HashSet<Address>())
                .build();
    }

    @DisplayName("JUnit test for savePerson method")
    @Test
    public void givenPersonObject_whenSavePerson_thenReturnPersonObject(){

        given(personRepository.save(personNoId)).willReturn(personNoId);

        // when -  action or the behaviour that we are going test
        Person savedPerson = personService.insertPerson(personNoId);

        // then - verify the output
        assertThat(savedPerson).isNotNull();
    }

    @DisplayName("JUnit test for getAllPersones method")
    @Test
    public void givenPersonsList_whenGetAllPersons_thenReturnPersonsList(){
        // given - precondition or setup

        Person person2 = Person.builder()
                .id(1L)
                .firstName("Stefano")
                .lastName("Pinti")
                .birthDate(LocalDate.of(1993, 1, 4))
                .mainAddress(new Address())
                .additionalAddresses(new HashSet<Address>())
                .build();

        given(personRepository.findAll()).willReturn(List.of(person, person2));

        // when -  action or the behaviour that we are going test
        List<Person> PersonList = personService.getAllPersons();

        // then - verify the output
        assertThat(PersonList).isNotNull();
        assertThat(PersonList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getPersonById method")
    @Test
    public void givenPersonId_whenGetPersonById_thenReturnPersonObject(){
        // given
        given(personRepository.findById(1L)).willReturn(Optional.of(person));

        // when
        Person savedPerson = personService.getSinglePersonById(person.getId()).get();

        // then
        assertThat(savedPerson).isNotNull();

    }


    // JUnit test for updatePerson method
    @DisplayName("JUnit test for updatePerson method")
    @Test
    public void givenPersonObject_whenUpdatePerson_thenReturnUpdatedPerson(){

        Person person2 = Person.builder()
                .id(1L)
                .firstName("Stefano")
                .lastName("Pinti")
                .birthDate(LocalDate.of(1993, 1, 4))
                .mainAddress(new Address())
                .additionalAddresses(new HashSet<Address>())
                .build();

        // given - precondition or setup
        given(personRepository.save(person2)).willReturn(person2);

        // when -  action or the behaviour that we are going test
        personRepository.save(person2);

        person2.setFirstName("Enfasto");
        person2.setLastName("Itnip");

        Person updatedPerson = personService.updatePerson(person2);

        // then - verify the output
        assertThat(updatedPerson.getFirstName()).isEqualTo("Enfasto");
        assertThat(updatedPerson.getLastName()).isEqualTo("Itnip");
    }

    // JUnit test for deletePerson method
    @DisplayName("JUnit test for deletePerson method")
    @Test
    public void givenPersonId_whenDeletePerson_thenNothing(){
        // given - precondition or setup
        long PersonId = 1L;

        willDoNothing().given(personRepository).delete(person);

        // when -  action or the behaviour that we are going test
        personService.removePerson(person);

        // then - verify the output
        verify(personRepository, times(1)).delete(person);
    }

}
