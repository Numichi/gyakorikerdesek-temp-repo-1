package org.example;

import org.example.csv.*;
import org.example.model.Person;
import org.example.repository.PersonRepository;

import java.io.File;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.example.csv.Validator.validate;

public class PersonRepositoryCSVImpl implements PersonRepository {
    private final File file;
    private final Service service;

    public PersonRepositoryCSVImpl(File file, Service service) {
        this.file = file;
        this.service = service;
    }

    @Override
    public List<Person> findAll() {
        return service.readCSV(file)
                .getAll()
                .stream().sorted(Comparator.comparingInt(Person::getId))
                .toList();
    }

    @Override
    public List<Person> findBornLaterThen(LocalDate date) {
        return service.readCSV(file)
                .getAll()
                .stream()
                .filter(person -> person.getBirthDay().isAfter(date))
                .sorted(Comparator.comparingInt(Person::getId))
                .toList();
    }

    @Override
    public List<Person> findByName(String name) {
        return service.readCSV(file)
                .getAll()
                .stream()
                .filter(person -> person.getName().equals(name))
                .sorted(Comparator.comparingInt(Person::getId))
                .toList();
    }

    @Override
    public Person findById(int id) {
        return service.readCSV(file).get(id);
    }

    @Override
    public void insert(Person person) {
        service.operate(file, warehouse -> {
            validate(person);
            warehouse.add(person);
        });
    }

    @Override
    public void delete(int id) {
        service.operate(file, warehouse -> warehouse.remove(id));
    }

    @Override
    public void update(Person person) {
        insert(person);
    }
}
