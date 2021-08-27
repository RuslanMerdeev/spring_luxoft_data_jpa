package com.example.spring_luxoft_data_jpa.rest;

import com.example.spring_luxoft_data_jpa.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryController {

    private final CountryRepository repository;

    @Autowired
    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/countries")
    List<CountryDto> getCountries() {
        return repository.findAll()
                .stream()
                .map(CountryDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/country/{id}")
    CountryDto getCountryById(@PathVariable int id) {
        return repository.findById(id)
                .map(CountryDto::toDto)
                .orElse(null);
    }

    @PostMapping("/country")
    CountryDto postCountry(@RequestBody CountryDto person) {
        if (!repository.existsById(person.getId())) {
            repository.save(CountryDto.toDomainObject(person));
            return person;
        }
        return null;
    }

    @PutMapping("/country")
    CountryDto putCountry(@RequestBody CountryDto person) {
        if (repository.existsById(person.getId())) {
            repository.deleteById(person.getId());
        }
        repository.save(CountryDto.toDomainObject(person));
        return person;
    }

    @DeleteMapping("/country/{id}")
    ResponseEntity<String> deleteCountryById(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.notFound().build();
    }
}
