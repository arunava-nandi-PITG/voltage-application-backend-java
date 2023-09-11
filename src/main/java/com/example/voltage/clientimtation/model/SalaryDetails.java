package com.example.voltage.clientimtation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tbl_salary")
public class SalaryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name must be required")
    private String name;
    @NotBlank(message = "Designation must be required")
    private String designation;
    @NotBlank(message = "Salary must be required")
    private BigDecimal salary; //60000.00
}
