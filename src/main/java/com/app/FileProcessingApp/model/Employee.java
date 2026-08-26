package com.app.FileProcessingApp.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private Long employeeId;
    private String name;
    private String department;
    private Double salary;
}
