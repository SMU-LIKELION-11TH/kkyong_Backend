package smu.likelion.kkyong.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class ReservationBuilder {

    public static String convert(String number, int digit) {
        String result = "";
        String numberString = String.valueOf(number);

        for (int i = 0; i < digit - numberString.length() ; i++) {
            result = result.concat("0");
        }
        result = result.concat(numberString);
        return result;
    }

    public static String createReservationNumber(String serviceId) {
        String number = "";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] tokens = date.split("-");

        for (String token : tokens) {
            number = number.concat(token);
        }

        number = number.concat(convert(serviceId, 4));

        Random random = new Random(); // 랜덤 객체 생성
        random.setSeed(System.currentTimeMillis());

        number = number.concat(convert(String.valueOf(random.nextInt(9999)), 4));
        return number;
    }
}
