package com.example.voltage.clientimtation.service.voltageService;

public interface VoltageService {
    String encryptionPhoneNumber (String[] phoneNumber);
    String decryptPhoneNumber (String[] phoneNumber);

    // TODO  : Implement this part
    String encryptSalary(String[] salary);
    String decryptSalary(String[] salary);

}
