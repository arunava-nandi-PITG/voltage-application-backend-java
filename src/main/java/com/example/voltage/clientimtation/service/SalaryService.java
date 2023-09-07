package com.example.voltage.clientimtation.service;

import com.example.voltage.clientimtation.model.SalaryDetails;
import com.example.voltage.clientimtation.model.security.EncodedSalary;

import java.util.List;

public interface SalaryService {

    List<SalaryDetails> findAll();
    SalaryDetails save(SalaryDetails salaryDetails);
    SalaryDetails findById(Long id);
    SalaryDetails update(Long id, SalaryDetails salaryDetails);
    void delete(Long id);
    String encryptSalary(String[] salary);
    String decryptSalary(String[] salary);
    SalaryDetails getSalaryById(Long id);
}
