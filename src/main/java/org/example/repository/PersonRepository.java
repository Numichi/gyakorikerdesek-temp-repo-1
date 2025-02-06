package org.example.repository;

import org.example.model.Person;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository {
    List<Person> findAll();
    List<Person> findBornLaterThen(LocalDate datum);
    List<Person> findByName(String nev);

    Person findById(int id);

    void insert(Person person);
    void delete(int id);
    void update(Person person);
}
