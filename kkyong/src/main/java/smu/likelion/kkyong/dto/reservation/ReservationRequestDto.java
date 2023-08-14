package smu.likelion.kkyong.dto.reservation;

import lombok.Data;

@Data
public class ReservationRequestDto {
    private String date;
    private String startTime;
    private String endTime;
    public ReservationRequestDto(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
