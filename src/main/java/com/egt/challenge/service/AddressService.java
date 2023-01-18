package com.egt.challenge.service;

import com.egt.challenge.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    public List<Address> getAllAddresses();

    public Optional<Address> getSingleAddressById(Long id);

    public Address insertAddress(Address address) throws UnsupportedOperationException;

    public Address updateAddress(Address address) throws UnsupportedOperationException;

    public void removeAddress(Address address) throws UnsupportedOperationException;
}
