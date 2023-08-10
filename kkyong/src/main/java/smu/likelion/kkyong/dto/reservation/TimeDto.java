package smu.likelion.kkyong.dto.reservation;

import lombok.Builder;
import lombok.Data;

@Data
public class TimeDto {

    private String startTime;
    private String endTime;

    @Builder
    public TimeDto(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Time toEntity() {
        return Time.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
