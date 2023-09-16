package com.example.voltage.clientimtation.serviceImplementation.voltageServiceImpl;

import com.example.voltage.clientimtation.Constants;
import com.example.voltage.clientimtation.dto.request.PhoneNumberRequest;
import com.example.voltage.clientimtation.dto.response.PhoneNumberResponse;
import com.example.voltage.clientimtation.model.security.EncodedSalary;
import com.example.voltage.clientimtation.model.security.SalaryRequestModel;
import com.example.voltage.clientimtation.service.voltageService.VoltageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.voltage.clientimtation.Constants.contentTypePair;
import static com.example.voltage.clientimtation.Constants.authorizationPair;

@Service
@RequiredArgsConstructor
public class VoltageServiceImpl implements VoltageService {

    private final RestTemplate restTemplate;

    @Override
    public String encryptionPhoneNumber(String[] phoneNumber) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(contentTypePair.getKey(), contentTypePair.getValue());
        httpHeaders.set(authorizationPair.getKey() , authorizationPair.getValue());

        HttpEntity<PhoneNumberRequest> httpEntity = new HttpEntity<>(new PhoneNumberRequest("PhoneNumber" , phoneNumber),httpHeaders);
        ResponseEntity<PhoneNumberResponse> encodePhoneNumberResponse = restTemplate.postForEntity(Constants.urlProtect , httpEntity, PhoneNumberResponse.class);
        if (encodePhoneNumberResponse.getBody()!= null) {
            return encodePhoneNumberResponse.getBody().getData()[0];
        }
        return null;

    }

    @Override
    public String decryptPhoneNumber(String[] phoneNumber) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(contentTypePair.getKey(), contentTypePair.getValue());
        httpHeaders.set(authorizationPair.getKey(), authorizationPair.getValue());

        HttpEntity<PhoneNumberRequest> httpEntity = new HttpEntity<>(new PhoneNumberRequest("phoneNumber", phoneNumber), httpHeaders);
        ResponseEntity<PhoneNumberResponse> decodePhoneNumberResponseBody = restTemplate.postForEntity(Constants.urlAccess, httpEntity, PhoneNumberResponse.class);
        if (decodePhoneNumberResponseBody.getBody()!= null) {
            return decodePhoneNumberResponseBody.getBody().getData()[0];
        }
        return null;
    }

    @Override
    public String encryptSalary(String[] salary) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(contentTypePair.getKey(), contentTypePair.getValue());
        httpHeaders.set(authorizationPair.getKey(), authorizationPair.getValue());

        HttpEntity<SalaryRequestModel> httpEntity = new HttpEntity<>(new SalaryRequestModel("salary", salary), httpHeaders);
        ResponseEntity<EncodedSalary> encodedSalaryResponseBody = restTemplate.postForEntity(Constants.urlAccess, httpEntity, EncodedSalary.class);
        if(encodedSalaryResponseBody.getBody() != null){
            return encodedSalaryResponseBody.getBody().getData()[0];
        }
        return null;
    }

    @Override
    public String decryptSalary(String[] salary) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(contentTypePair.getKey(), contentTypePair.getValue());
        httpHeaders.set(authorizationPair.getKey(), authorizationPair.getValue());

        HttpEntity<SalaryRequestModel> httpEntity = new HttpEntity<>(new SalaryRequestModel("salary", salary), httpHeaders);
        ResponseEntity<EncodedSalary> decodedSalaryResponseBody = restTemplate.postForEntity(Constants.urlAccess, httpEntity, EncodedSalary.class);
        if(decodedSalaryResponseBody.getBody() != null){
            return decodedSalaryResponseBody.getBody().getData()[0];
        }
        return null;
    }
}
