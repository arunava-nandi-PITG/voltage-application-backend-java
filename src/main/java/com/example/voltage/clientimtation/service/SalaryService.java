package com.example.voltage.clientimtation.service;

import com.example.voltage.clientimtation.model.SalaryDetails;

import java.util.List;

public interface SalaryService {

    List<SalaryDetails> findAll();
    SalaryDetails save(SalaryDetails salaryDetails);
    SalaryDetails findById(Long id);
    SalaryDetails update(Long id, SalaryDetails salaryDetails);
    void delete(Long id);
    SalaryDetails getSalaryById(Long id);
}
