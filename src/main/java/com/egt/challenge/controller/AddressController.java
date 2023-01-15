package com.egt.challenge.controller;

import com.egt.challenge.dto.AddressDto;
import com.egt.challenge.service.AddressService;
import com.egt.challenge.service.PersonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(AddressController.BASE_URL)
@RequiredArgsConstructor
public class AddressController {
    public static final String BASE_URL = "/api/addresses";

    @NonNull
    private final AddressService addressService;
    @NonNull
    private final PersonService personService;

    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> getAll() {
        ArrayList addressList = new ArrayList<>();
        addressList.add(new AddressDto());
        addressList.add(new AddressDto());
        addressList.add(new AddressDto());

        return ResponseEntity.ok().body(addressList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(new AddressDto());
    }

    @PostMapping
    public ResponseEntity<AddressDto> create(@RequestBody AddressDto address) {
        return ResponseEntity.created(URI.create("/api/addresses/id_goes_here")).body(new AddressDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateByID(@RequestBody AddressDto address, @PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDto> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok().body(null);
    }
}
