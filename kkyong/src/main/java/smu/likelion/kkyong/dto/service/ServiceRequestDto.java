package smu.likelion.kkyong.dto.service;

import lombok.Builder;
import lombok.Data;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.enums.ServiceType;


@Data
public class ServiceRequestDto {
    private String serviceType;
    private String serviceName;
    private String region;
    private String place;
    private String serviceTarget;
    private String serviceStart;
    private String serviceEnd;
    private String imageUrl;
    private String contact;
    private boolean reservationStatus;

    @Builder
    public ServiceRequestDto(String serviceType, String serviceName, String region, String place,
                             String serviceTarget, String serviceStart, String serviceEnd,
                             String imageUrl, String contact, boolean reservationStatus) {
        this.serviceType = serviceType;
        this.serviceName = serviceName;
        this.region = region;
        this.place = place;
        this.serviceTarget = serviceTarget;
        this.serviceStart = serviceStart;
        this.serviceEnd = serviceEnd;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.reservationStatus = reservationStatus;
    }

    public Services toEntity() {
        return Services.builder()
                .serviceType(ServiceType.findByName(this.serviceType))
                .serviceName(this.serviceName)
                .region(this.region)
                .place(this.place)
                .serviceTarget(this.serviceTarget)
                .serviceStart(convertDate(this.serviceStart))
                .serviceEnd(convertDate(this.serviceEnd))
                .imageUrl(this.imageUrl)
                .contact(this.contact)
                .reservationStatus(this.reservationStatus)
                .build();
    }

    private String convertDate(String date) {
        return date.substring(0, 11) + "00:00:00";
    }

}
