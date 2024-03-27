package br.com.customer.util;

import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class Util {

    public static boolean isValidYear(int year) {
        int currentYear = Year.now().getValue();
        return year >= 0 && year <= currentYear;
    }

}
