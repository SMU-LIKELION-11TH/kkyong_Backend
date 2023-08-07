package smu.likelion.kkyong.dto.service;

import lombok.Builder;
import lombok.Data;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.enums.ServiceType;

@Data
public class ServiceReturnDto {
    private Long id;
    private ServiceType serviceType;
    private String serviceName;
    private String region;
    private String place;
    private String serviceTarget;
    private String xCoord;
    private String yCoord;
    private String serviceStart;
    private String serviceEnd;
    private String imageUrl;
    private String contact;
    private int recruitCount;
    private boolean reservationStatus;

    @Builder
    public ServiceReturnDto(Services entity) {
        this.id = entity.getId();
        this.serviceType = entity.getServiceType();
        this.serviceName = entity.getServiceName();
        this.region = entity.getRegion();
        this.place = entity.getPlace();
        this.serviceTarget = entity.getServiceTarget();
        this.xCoord = entity.getXCoord();
        this.yCoord = entity.getYCoord();
        this.serviceStart = entity.getServiceStart();
        this.serviceEnd = entity.getServiceEnd();
        this.imageUrl = entity.getImageUrl();
        this.contact = entity.getContact();
        this.recruitCount = entity.getRecruitCount();
        this.reservationStatus = entity.getReservationStatus();
    }
}
