package com.egt.challenge.service;

import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;
import com.egt.challenge.repo.AddressRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    @NonNull
    private final AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        List<Address> allAddresses = addressRepository.findAll();

        Comparator<Address> comparator = Comparator.comparing(address -> address.getStreet1());
        comparator = comparator.thenComparing(Comparator.comparing(address -> address.getState()));

        return allAddresses.stream().sorted(comparator).toList();
    }

    @Override
    public Optional<Address> getSingleAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address insertAddress(Address address) throws UnsupportedOperationException {
        if(address.getId() != null) {
            throw new UnsupportedOperationException();
        }

        addressRepository.save(address);

        return address;
    }

    @Override
    public Address updateAddress(Address address) throws UnsupportedOperationException {
        if(address.getId() == null) {
            throw new UnsupportedOperationException("Cannot update a address without an ID.");
        }
        else if (address.getPerson() == null) {
            throw new UnsupportedOperationException("Cannot update a address to have a null main address.");
        }

        Optional<Address> oldRecordOptional = addressRepository.findById(address.getId());

        if (oldRecordOptional.isPresent()) {
            addressRepository.save(address);
            return address;
        }
        else {
            throw new UnsupportedOperationException("The address with id (" + address.getId() + ") could not be found.");
        }
    }

    @Override
    public void removeAddress(Address address) throws UnsupportedOperationException {
        addressRepository.delete(address);
    }
}
