package com.example.voltage.clientimtation.serviceImplementation;

import com.example.voltage.clientimtation.exception.FormatNotValidException;
import com.example.voltage.clientimtation.exception.UserNotFoundException;
import com.example.voltage.clientimtation.model.SalaryDetails;
import com.example.voltage.clientimtation.repository.SalaryRepository;
import com.example.voltage.clientimtation.service.SalaryService;
import com.example.voltage.clientimtation.service.voltageService.VoltageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final VoltageService voltageService;


    @Override
    public List<SalaryDetails> findAll() {
        return salaryRepository.findAll();
    }

    @Override
    public SalaryDetails save(SalaryDetails salaryDetails) {
        String[] salary = new String[1];
        salary[0] = salaryDetails.getSalary().toString();
        //if salary doesn't contain decimal point condition true or salary contains decimal point condition true.
        if (!salary[0].contains(".")) {
            salary[0] = salary[0] + ".00";
        }
        if(salary[0].contains(".")) {
            salary[0] = salary[0];
        }
        else {
            throw new FormatNotValidException("data not valid  format. example format: `100000.00`");
        }
        BigDecimal encryptedSalary = new BigDecimal(encryptSalary(salary[0]));
        salaryDetails.setSalary(encryptedSalary);
        salaryRepository.save(salaryDetails);
        return salaryDetails;
    }

    @Override
    public SalaryDetails findById(Long id) {
        if (salaryRepository.findById(id).isPresent()) {
            String[] salary = new String[1];
            salary[0] = salaryRepository.findById(id).get().getSalary().toString();
            BigDecimal decryptedSalary = new BigDecimal(decryptSalary(salary[0]));
            SalaryDetails actualDetails = salaryRepository.findById(id).get();
            actualDetails.setSalary(decryptedSalary);
            return actualDetails;
        } else {
            return null;
        }
    }

    @Override
    public SalaryDetails update(Long id, SalaryDetails salaryDetails) {
        SalaryDetails actualDetails = findById(id);
        if (Objects.nonNull(salaryDetails.getName())
                && !"".equalsIgnoreCase(
                salaryDetails.getName())) {
            actualDetails.setName(salaryDetails.getName());
        }
        if (Objects.nonNull(salaryDetails.getDesignation())
                && !"".equalsIgnoreCase(
                salaryDetails.getDesignation())) {
            actualDetails.setDesignation(salaryDetails.getDesignation());
        }
        if (Objects.nonNull(salaryDetails.getSalary())
                && !"".equalsIgnoreCase(
                String.valueOf(salaryDetails.getSalary()))) {
            actualDetails.setSalary(salaryDetails.getSalary());
        }

        return save(actualDetails);
    }

    // TODO: RETURN PROPER RESPONSE MESSAGE
    @Override
    public void delete(Long id) {
        SalaryDetails salaryDetails = salaryRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for " + id));
        if (salaryDetails != null) {
            salaryRepository.delete(salaryDetails);
        }
    }

    @Override
    public SalaryDetails getSalaryById(Long id) {
        return salaryRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User id not found : " + id));
    }

    private String encryptSalary(String actualSalary) {
        String[] actualSalaryList = new String[1];
        actualSalaryList[0] = actualSalary;
        return voltageService.encryptSalary(actualSalaryList);
    }

    private String decryptSalary(String salary) {
        String[] salaryList  = new String[1];
        salaryList[0] = salary;
        return voltageService.decryptSalary(salaryList);
    }

}
