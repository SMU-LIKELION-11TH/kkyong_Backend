package smu.likelion.kkyong.dto.reservation;

import lombok.Builder;
import lombok.Data;
import smu.likelion.kkyong.domain.entity.Reservation;
import smu.likelion.kkyong.domain.entity.Services;

@Data
public class ReservationReturnDto {
    private String reservationNumber;
    private String reservationDate;
    private String startTime;
    private String endTime;
    private String serviceName;
    private String serviceTarget;
    private String serviceType;
    private String imageUrl;
    private String region;
    private String place;
    private String contact;

    @Builder
    public ReservationReturnDto(Reservation reservation, Services service) {
        this.reservationNumber = reservation.getReservationNumber();
        this.reservationDate = reservation.getReservationDate();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
        this.serviceName = service.getServiceName();
        this.serviceTarget = service.getServiceTarget();
        this.serviceType = service.getServiceType().getTypeName();
        this.imageUrl = service.getImageUrl();
        this.region = service.getRegion();
        this.place = service.getPlace();
        this.contact = service.getContact();
    }
}
