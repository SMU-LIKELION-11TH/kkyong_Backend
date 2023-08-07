package smu.likelion.kkyong.dto.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.enums.ServiceType;

import java.util.List;
import java.util.Map;

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
    private boolean bookmarkStatus;

    @Builder
    public ServiceRequestDto(String serviceType, String reservationType, String serviceName, String region, String place,
                             String serviceTarget, String xCoord, String yCoord, String serviceStart, String serviceEnd,
                             String imageUrl, String contact, int recruitCount, boolean reservationStatus, boolean bookmarkStatus) {
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
        this.bookmarkStatus = bookmarkStatus;
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
                .bookmarkStatus(this.bookmarkStatus)
                .build();
    }

    private String convertDate(String date) {
        return date.substring(0, 11) + " 00:00:00";
    }

}
