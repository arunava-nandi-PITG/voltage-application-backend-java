package com.example.voltage.clientimtation.controller;

import com.example.voltage.clientimtation.model.SalaryDetails;
import com.example.voltage.clientimtation.service.SalaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping("/salarylist")
    public ResponseEntity<List<SalaryDetails>> getSalaryList() {
        List<SalaryDetails> salaryDetailsList = salaryService.findAll();
        return new ResponseEntity<>(salaryDetailsList, HttpStatus.OK);
    }

    @PostMapping("/savesalary")
    public ResponseEntity<SalaryDetails> saveSalary(@Valid @RequestBody SalaryDetails salaryDetails) {
        SalaryDetails savedSalaryDetails = salaryService.save(salaryDetails);
        return new ResponseEntity<>(savedSalaryDetails, HttpStatus.CREATED);
    }

    @GetMapping("/getsalary/{id}")
    public ResponseEntity<SalaryDetails> getSalary(@PathVariable("id") Long id) {
        SalaryDetails salaryDetails = salaryService.findById(id);
        return new ResponseEntity<>(salaryDetails, HttpStatus.OK);
    }

    @PutMapping("/updatesalary/{id}")
    public ResponseEntity<SalaryDetails> updateSalary(@PathVariable("id") Long id,@Valid  @RequestBody SalaryDetails salaryDetails) {
        SalaryDetails updatedSalaryDetails = salaryService.update(id, salaryDetails);
        return new ResponseEntity<>(updatedSalaryDetails, HttpStatus.OK);
    }

    @DeleteMapping("/deletesalary/{id}")
    public ResponseEntity<String> deleteSalary(@PathVariable("id") Long id) {
        salaryService.delete(id);
        return new ResponseEntity<>("Salary Record Deleted", HttpStatus.OK);
    }

//    @PostMapping("/getactualsalary")
//    public String getActualSalary(@RequestBody String fakeSalary) {
//        String[] salary = {fakeSalary};
//        return salaryService.decryptSalary(salary);
//    }

    @GetMapping("/salary/{id}")
    public SalaryDetails getSalaryById(@PathVariable Long id){
        return salaryService.getSalaryById(id);
    }
}
