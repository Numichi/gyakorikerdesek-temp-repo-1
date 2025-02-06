package org.example.csv;

import org.example.model.Person;

import java.util.*;

public class DataWarehouse {
  private final Map<Integer, Person> personMap = new LinkedHashMap<>();

  public Collection<Person> getAll() {
    return personMap.values();
  }

  public void add(Person person) {
    personMap.put(person.getId(), person);
  }

  public void remove(int id) {
    personMap.remove(id);
  }

  public Person get(int id) {
    return personMap.get(id);
  }
}
