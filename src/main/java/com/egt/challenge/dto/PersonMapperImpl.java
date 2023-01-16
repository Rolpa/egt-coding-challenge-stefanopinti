package com.egt.challenge.dto;

import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class PersonMapperImpl implements PersonMapper {
    @Override
    public PersonDto toDto(Person entity) {
        System.out.println(entity);

        PersonDto dto = new PersonDto(entity.getId(),entity.getFirstName(), entity.getLastName(), entity.getBirthDate(),
                entity.getMainAddress().getStreet1(), entity.getMainAddress().getStreet2(), entity.getMainAddress().getCity(),
                entity.getMainAddress().getState(), entity.getMainAddress().getZipCode(), null );
        return dto;
    }

    @Override
    public Person toEntity(PersonDto dto) {
        Address address = new Address();

        address.setStreet1(dto.getStreet1());
        address.setStreet2(dto.getStreet2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());

        Person entity = new Person(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getBirthDate(), address, new HashSet<Address>());
        return entity;
    }
}
