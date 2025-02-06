package org.example.csv;

import org.example.model.Person;
import org.example.exception.PersonDAOException;

import java.io.*;
import java.util.function.Consumer;

public class Service {
    public DataWarehouse readCSV(File file) {
        var data = new DataWarehouse();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                var validator = new Validator(line);
                data.add(validator.isValid());
            }
        } catch (IOException e) {
            throw new PersonDAOException("Error while reading " + file.getName() + ".", e);
        }

        return data;
    }

    private void writeCsv(File file, DataWarehouse dataWarehouse) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Person person : dataWarehouse.getAll()) {
                String line = String.format("%d,%s,%s,%s",
                        person.getId(),
                        person.getName(),
                        person.getEmail(),
                        person.getBirthDay().toString());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new PersonDAOException("Error while writing " + file.getName() + ".", e);
        }
    }

    public void operate(File file, Consumer<DataWarehouse> consumer) {
        var dataWarehouse = readCSV(file);
        consumer.accept(dataWarehouse);
        writeCsv(file, dataWarehouse);
    }
}
