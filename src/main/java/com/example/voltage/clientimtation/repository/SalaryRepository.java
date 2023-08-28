package com.example.voltage.clientimtation.repository;

import com.example.voltage.clientimtation.model.SalaryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryDetails, Long> {
}
