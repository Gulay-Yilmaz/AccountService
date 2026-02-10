package com.example.accountservice.service;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;

@Component
public class IbanGenerator {

    private static final String COUNTRY_CODE = "TR";
    private static final String BANK_CODE = "00061";

    public String generate() {
        String accountNumber = generateAccountNumber();
        String reserved = "0";

        String ibanWithoutCheck =
                COUNTRY_CODE + "00" + BANK_CODE + reserved + accountNumber;

        String checkDigits = calculateCheckDigits(ibanWithoutCheck);

        return COUNTRY_CODE + checkDigits + BANK_CODE + reserved + accountNumber;
    }

    private String generateAccountNumber() {
        return String.format("%016d", new SecureRandom().nextLong(1_0000_0000_0000_0000L));
    }

    private String calculateCheckDigits(String iban) {
        String rearranged =
                iban.substring(4) + iban.substring(0, 4)
                        .replace("T", "29")
                        .replace("R", "27");

        BigInteger number = new BigInteger(rearranged);
        int mod97 = number.mod(BigInteger.valueOf(97)).intValue();

        return String.format("%02d", 98 - mod97);
    }
}

