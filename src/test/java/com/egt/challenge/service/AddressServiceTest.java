package com.egt.challenge.service;

import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;
import com.egt.challenge.repo.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {


    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;

    private Address addressNoId;

    @BeforeEach
    public void setup(){

        address = Address.builder()
                .id(1L)
                .person(new Person())
                .street1("1234 South Street")
                .street2("Unit A1")
                .city("Philadelphia")
                .zipCode("19080")
                .build();

        addressNoId = Address.builder()
                .person(new Person())
                .street1("5678 South Street")
                .street2("Unit A2")
                .city("Philadelphia")
                .zipCode("19080")
                .build();
    }

    @DisplayName("JUnit test for saveAddress method")
    @Test
    public void givenAddressObject_whenSaveAddress_thenReturnAddressObject(){

        given(addressRepository.save(addressNoId)).willReturn(addressNoId);

        // when -  action or the behaviour that we are going test
        Address savedaddress = addressService.insertAddress(addressNoId);

        // then - verify the output
        assertThat(savedaddress).isNotNull();
    }

    @DisplayName("JUnit test for getAllAddresses method")
    @Test
    public void givenAddressesList_whenGetAllAddresses_thenReturnAddressesList(){
        // given - precondition or setup

        Address address2 = Address.builder()
                .person(new Person())
                .street1("8765 South Street")
                .street2("Unit A4")
                .city("Philadelphia")
                .zipCode("19080")
                .build();

        given(addressRepository.findAll()).willReturn(List.of(address, address2));

        // when -  action or the behaviour that we are going test
        List<Address> addressList = addressService.getAllAddresses();

        // then - verify the output
        assertThat(addressList).isNotNull();
        assertThat(addressList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAddressById method")
    @Test
    public void givenAddressId_whenGetAddressById_thenReturnAddressObject(){
        // given
        given(addressRepository.findById(1L)).willReturn(Optional.of(address));

        // when
        Address savedAddress = addressService.getSingleAddressById(address.getId()).get();

        // then
        assertThat(savedAddress).isNotNull();

    }


    // JUnit test for updateAddress method
    @DisplayName("JUnit test for updateAddress method")
    @Test
    public void givenAddressObject_whenUpdateAddress_thenReturnUpdatedAddress(){

        Address address2 = Address.builder()
                .id(1L)
                .person(new Person())
                .street1("8765 South Street")
                .street2("Unit A4")
                .city("Philadelphia")
                .zipCode("19080")
                .build();

        // given - precondition or setup
        given(addressRepository.save(address2)).willReturn(address2);

        // when -  action or the behaviour that we are going test
        addressRepository.save(address2);

//        address2.setStreet1("10 Spruce Street");
//        address2.setStreet2("Unit C3P0");

        Address updatedAddress = addressService.updateAddress(address2);

        // then - verify the output
        assertThat(updatedAddress.getStreet1()).isEqualTo("10 Spruce Street");
        assertThat(updatedAddress.getStreet1()).isEqualTo("Unit C3P0");
    }

    // JUnit test for deleteAddress method
    @DisplayName("JUnit test for deleteAddress method")
    @Test
    public void givenAddressId_whenDeleteAddress_thenNothing(){
        // given - precondition or setup
        long addressId = 1L;

        willDoNothing().given(addressRepository).delete(address);

        // when -  action or the behaviour that we are going test
        addressService.removeAddress(address);

        // then - verify the output
        verify(addressRepository, times(1)).delete(address);
    }
}
