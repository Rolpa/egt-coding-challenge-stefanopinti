package com.egt.challenge.controller;

import com.egt.challenge.dto.AddressDto;
import com.egt.challenge.dto.AddressMapper;
import com.egt.challenge.dto.PersonDto;
import com.egt.challenge.model.Address;
import com.egt.challenge.model.Person;
import com.egt.challenge.service.AddressService;
import com.egt.challenge.service.PersonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AddressController.BASE_URL)
@RequiredArgsConstructor
public class AddressController {
    public static final String BASE_URL = "/api/addresses";

    @NonNull
    private final AddressService addressService;
    @NonNull
    private final PersonService personService;

    @NonNull
    private final AddressMapper addressMapper;

    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> getAll() {
        List<AddressDto> addressList = addressService.getAllAddresses().stream().map(entity -> addressMapper.toDto(entity)).collect(Collectors.toList());

        return ResponseEntity.ok().body(addressList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@RequestBody AddressDto dto, @PathVariable Long id) {
        Optional<Address> addressOptional = addressService.getSingleAddressById(id);

        if (addressOptional.isPresent()) {
            AddressDto addressDto = addressMapper.toDto(addressService.getSingleAddressById(id).get());
            return ResponseEntity.ok().body(addressDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AddressDto address) {

        Address newaddress = addressMapper.toEntity(address);
        try {
            addressService.insertAddress(newaddress);
        }
        catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.created(URI.create("/api/addresses/id_goes_here")).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateByID(@RequestBody AddressDto address, @PathVariable Long id) {
        Address newAddress = addressMapper.toEntity(address);
        try {
            addressService.insertAddress(newAddress);
        }
        catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDto> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }
}
