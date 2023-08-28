package com.example.voltage.clientimtation.serviceImplementation;

import com.example.voltage.clientimtation.exception.FormatNotValidException;
import com.example.voltage.clientimtation.exception.UserNotFoundException;
import com.example.voltage.clientimtation.model.SalaryDetails;
import com.example.voltage.clientimtation.model.security.EncodedSalary;
import com.example.voltage.clientimtation.model.security.SalaryRequestModel;
import com.example.voltage.clientimtation.repository.SalaryRepository;
import com.example.voltage.clientimtation.service.SalaryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final RestTemplate restTemplate;

    String urlProtect = "https://172.16.163.90/vibesimple/rest/v1/protect";
    String urlAccess = "https://172.16.163.90/vibesimple/rest/v1/access";

    @Override
    public List<SalaryDetails> findAll() {
        return salaryRepository.findAll();
    }

    @Override
    public SalaryDetails save(SalaryDetails salaryDetails) {
        String[] salary = new String[1];
        salary[0] = salaryDetails.getSalary().toString();
        //if salary doesn't contain decimal point condition true or salary contains decimal point condition true.
//        || (!salary[0].contains(".00"))
        if((!salary[0].contains(".")) || !salary[0].contains(".00")) {
            salary[0] = salary[0]+".00";
        }else{
            System.out.println("data not valid  format. example format: `100000.00`");
            throw new FormatNotValidException("data not valid  format. example format: `100000.00`");
        }
        BigDecimal encryptedSalary = new BigDecimal(encryptSalary(salary));
        salaryDetails.setSalary(encryptedSalary);
        salaryRepository.save(salaryDetails);
        return salaryDetails;
    }

    @Override
    public SalaryDetails findById(Long id) {
        if (salaryRepository.findById(id).isPresent()) {
            String[] salary = new String[1];
            salary[0] = salaryRepository.findById(id).get().getSalary().toString();
            BigDecimal decryptedSalary = new BigDecimal(decryptSalary(salary));
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
       SalaryDetails salaryDetails =  salaryRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found for " +id));
        salaryRepository.delete(salaryDetails);
    }

    @Override
    public String encryptSalary(String[] salary) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", "VSAuth vsauth_method=\"sharedSecret\", vsauth_data=\"JCVmcmlzbyMyISkyNTZE\", vsauth_identity_ascii=\"anandi@opentext.com\", vsauth_version=\"200\"");

        HttpEntity<SalaryRequestModel> httpEntity = new HttpEntity<>(new SalaryRequestModel("salary", salary), httpHeaders);
        ResponseEntity<EncodedSalary> encodedSalaryResponseBody = restTemplate.postForEntity(urlProtect, httpEntity, EncodedSalary.class);
        return encodedSalaryResponseBody.getBody().getData()[0];
    }

    @Override
    public String decryptSalary(String[] salary) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", "VSAuth vsauth_method=\"sharedSecret\", vsauth_data=\"JCVmcmlzbyMyISkyNTZE\", vsauth_identity_ascii=\"anandi@opentext.com\", vsauth_version=\"200\"");

        HttpEntity<SalaryRequestModel> httpEntity = new HttpEntity<>(new SalaryRequestModel("salary", salary), httpHeaders);
        ResponseEntity<EncodedSalary> decodedSalaryResponseBody = restTemplate.postForEntity(urlAccess, httpEntity, EncodedSalary.class);
        return decodedSalaryResponseBody.getBody().getData()[0];
    }


    @Override
    public SalaryDetails getSalaryById(Long id) {
        return salaryRepository.findById(id).orElseThrow(()->new UserNotFoundException("User id not found : " + id));
    }
}
