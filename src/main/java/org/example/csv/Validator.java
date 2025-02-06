package org.example.csv;

import org.example.model.Person;
import org.example.exception.PersonDAOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validator {
    private static final String SEPARATOR = ",";
    private static final String ID = "sorszám";
    private static final String NAME = "név";
    private static final String EMAIL = "email";
    private static final String DATE = "dátum";
    private static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String NAME_REGEX = "^[. \\p{L}]+$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final String[] parts;

    public Validator(String line) {
        this.parts = line.split(SEPARATOR);
    }

    public static void isValidEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new PersonDAOException(strTemplate(EMAIL));
        }
    }

    public static void isValidName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new PersonDAOException(strTemplate(NAME));
        }
    }

    public static void validate(Person person) {
        isValidEmail(person.getEmail());
        isValidName(person.getName());
    }

    public Person isValid() throws PersonDAOException {
        if (parts.length != 4) {
            throw new PersonDAOException("Incorrect number of fields.");
        }

        var result = new Person();
        checkId(result);
        checkDate(result);
        result.setName(parts[1]);
        result.setEmail(parts[2]);

        validate(result);

        return result;
    }

    private void checkDate(Person result) {
        try {
            result.setBirthDay(LocalDate.parse(parts[3], DATE_FORMATTER));
        } catch (DateTimeParseException e) {
            throw new PersonDAOException(strTemplate(DATE), e);
        }
    }

    private void checkId(Person result) {
        try {
            result.setId(Integer.parseInt(parts[0]));
        } catch (NumberFormatException e) {
            throw new PersonDAOException(strTemplate(ID), e);
        }
    }

    private static String strTemplate(String str) {
        return "Invalid format for field: " + str;
    }
}
