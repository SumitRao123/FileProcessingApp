package com.app.FileProcessingApp.service;

import com.app.FileProcessingApp.model.Employee;
import com.app.FileProcessingApp.model.FileProcessingResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Service
public class FileProcessingService {



     public FileProcessingResult process(InputStream inputStream){
            FileProcessingResult result = new FileProcessingResult();

            Reader reader  = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
          CSVFormat format = CSVFormat.DEFAULT.builder().
                                   setHeader().
                                   setSkipHeaderRecord(true).
                                   setIgnoreEmptyLines(true).
                                   build();
          try (CSVParser parser =
                       format.parse(reader)) {

               for (CSVRecord record : parser) {

                    result.incrementTotal();

                    try {

                         Employee employee =
                                 parseEmployee(record);

                         validateEmployee(employee);

                         processEmployee(employee);

                         result.incrementSuccessful();

                    } catch (Exception e) {

                         result.incrementFailed();

                         System.err.println(
                                 "Failed to process row "
                                         + record.getRecordNumber()
                                         + ": "
                                         + e.getMessage()
                         );
                    }
               }
          } catch (IOException e) {
                   throw new RuntimeException(e);
          }

         return result;
     }

     private Employee parseEmployee(
             CSVRecord record) {

          Long employeeId =
                  Long.parseLong(
                          record.get("employeeId")
                  );

          String name =
                  record.get("name").trim();

          String department =
                  record.get("department").trim();

          Double salary =
                  Double.parseDouble(
                          record.get("salary")
                  );

          return new Employee(
                  employeeId,
                  name,
                  department,
                  salary
          );
     }

     private void validateEmployee(
             Employee employee) {

          if (employee.getEmployeeId() == null) {
               throw new IllegalArgumentException(
                       "Employee ID is required"
               );
          }

          if (employee.getName() == null ||
                  employee.getName().isBlank()) {

               throw new IllegalArgumentException(
                       "Name is required"
               );
          }

          if (employee.getDepartment() == null ||
                  employee.getDepartment().isBlank()) {

               throw new IllegalArgumentException(
                       "Department is required"
               );
          }

          if (employee.getSalary() == null ||
                  employee.getSalary() <= 0) {

               throw new IllegalArgumentException(
                       "Salary must be greater than 0"
               );
          }
     }
     private void processEmployee(
             Employee employee) {

          System.out.println(
                  "Processing employee: "
                          + employee
          );
     }
}
