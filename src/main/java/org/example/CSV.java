package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.csv.Service;
import org.example.exception.PersonDAOException;
import org.example.model.Person;
import org.example.repository.PersonRepository;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public class CSV {

    public static void main(String[] args) {
        if (args.length < 1 || args[0].isBlank()) {
            log.error("Please provide the path to the CSV file");
            System.exit(1);
        }

        PersonRepository personRepository = new PersonRepositoryCSVImpl(new File(args[0]), new Service());

        try {
            printList(personRepository.findAll());
            personRepository.insert(new Person(8,"De Zsolt","dezso@baktaloranthaza.hu", LocalDate.of(1952, 1, 27)));
            printList(personRepository.findAll());
            personRepository.delete(8);
            printList(personRepository.findAll());
        } catch (PersonDAOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void printList(List<Person> list) {
        for (Person person : list) {
            log.info("{},{},{},{}", person.getId(), person.getName(), person.getEmail(), person.getBirthDay());
        }

        log.info("-------------------------------------------------");
    }
}
