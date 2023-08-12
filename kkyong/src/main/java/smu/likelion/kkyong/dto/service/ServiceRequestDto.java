package smu.likelion.kkyong.dto.service;

import lombok.Builder;
import lombok.Data;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.enums.ServiceType;


@Data
public class ServiceRequestDto {
    private String serviceType;
    private String reservationType;
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
    public ServiceRequestDto(String serviceType, String reservationType, String serviceName, String region, String place,
                             String serviceTarget, String xCoord, String yCoord, String serviceStart, String serviceEnd,
                             String imageUrl, String contact, int recruitCount, boolean reservationStatus) {
        this.serviceType = serviceType;
        this.reservationType = reservationType;
        this.serviceName = serviceName;
        this.region = region;
        this.place = place;
        this.serviceTarget = serviceTarget;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.serviceStart = serviceStart;
        this.serviceEnd = serviceEnd;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.recruitCount = recruitCount;
        this.reservationStatus = reservationStatus;
    }

    public Services toEntity() {
        return Services.builder()
                .serviceType(ServiceType.findByName(this.serviceType))
                .reservationType(this.reservationType)
                .serviceName(this.serviceName)
                .region(this.region)
                .place(this.place)
                .serviceTarget(this.serviceTarget)
                .xCoord(this.xCoord)
                .yCoord(this.yCoord)
                .serviceStart(convertDate(this.serviceStart))
                .serviceEnd(convertDate(this.serviceEnd))
                .imageUrl(this.imageUrl)
                .contact(this.contact)
                .recruitCount(this.recruitCount)
                .reservationStatus(this.reservationStatus)
                .build();
    }

    private String convertDate(String date) {
        return date.substring(0, 11) + " 00:00:00";
    }

}
