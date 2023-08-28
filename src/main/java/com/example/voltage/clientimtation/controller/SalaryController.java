package com.example.voltage.clientimtation.controller;

import com.example.voltage.clientimtation.model.SalaryDetails;
import com.example.voltage.clientimtation.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping("/salarylist")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER)")
    public ResponseEntity<List<SalaryDetails>> getSalaryList() {
        List<SalaryDetails> salaryDetailsList = salaryService.findAll();
        return new ResponseEntity<>(salaryDetailsList, HttpStatus.OK);
    }

    @PostMapping("/savesalary")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER)")
    public ResponseEntity<SalaryDetails> saveSalary(@RequestBody SalaryDetails salaryDetails) {
        SalaryDetails savedSalaryDetails = salaryService.save(salaryDetails);
        return new ResponseEntity<>(savedSalaryDetails, HttpStatus.OK);
    }

    @GetMapping("/getsalary/{id}")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER)")
    public ResponseEntity<SalaryDetails> getSalary(@PathVariable("id") Long id) {
        SalaryDetails salaryDetails = salaryService.findById(id);
        return new ResponseEntity<>(salaryDetails, HttpStatus.OK);
    }

    @PutMapping("/updatesalary/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SalaryDetails> updateSalary(@PathVariable("id") Long id, @RequestBody SalaryDetails salaryDetails) {
        SalaryDetails updatedSalaryDetails = salaryService.update(id, salaryDetails);
        return new ResponseEntity<>(updatedSalaryDetails, HttpStatus.OK);
    }

    @DeleteMapping("/deletesalary/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteSalary(@PathVariable("id") Long id) {
        salaryService.delete(id);
        return new ResponseEntity<>("Salary Record Deleted", HttpStatus.OK);
    }

    @PostMapping("/getactualsalary")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER)")
    public String getActualSalary(@RequestBody String fakeSalary) {
        String[] salary = {fakeSalary};
        return salaryService.decryptSalary(salary);
    }

    @GetMapping("/salary/{id}")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public SalaryDetails getSalaryById(@PathVariable Long id){
        return salaryService.getSalaryById(id);
    }
}
