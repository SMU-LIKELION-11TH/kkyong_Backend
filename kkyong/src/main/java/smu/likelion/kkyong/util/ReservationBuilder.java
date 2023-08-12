package smu.likelion.kkyong.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ReservationBuilder {
    private String number;

    public String createReservationNumber(String serviceId) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] tokens = date.split("-");

        for (String token : tokens) {
            number = number.concat(token);
        }

        if (serviceId.length() == 4) {
            number = number.concat(serviceId);
        } else if (serviceId.length() == 3) {
            number = number.concat("0" + serviceId);
        } else if (serviceId.length() == 2) {
            number = number.concat("00" + serviceId);
        } else {
            number = number.concat("000" + serviceId);
        }

        return this.number;
    }
}
