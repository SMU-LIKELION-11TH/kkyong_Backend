package smu.likelion.kkyong.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smu.likelion.kkyong.domain.enums.ServiceType;
import smu.likelion.kkyong.dto.service.ServiceReturnDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "services")
public class Services extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type")
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_target")
    private String serviceTarget;

    @Column
    private String region;

    @Column
    private String place;

    @Column(name = "service_start")
    private String serviceStart;

    @Column(name = "service_end")
    private String serviceEnd;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private String contact;

    @Column(name = "reservation_status")
    private Boolean reservationStatus;

    @OneToMany(mappedBy = "service")
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public Services(ServiceType serviceType, String serviceName, String serviceTarget,
                    String region, String place, String serviceStart, String serviceEnd,
                    String imageUrl, String contact, Boolean reservationStatus) {
        this.serviceType = serviceType;
        this.serviceName = serviceName;
        this.serviceTarget = serviceTarget;
        this.region = region;
        this.place = place;
        this.serviceStart = serviceStart;
        this.serviceEnd = serviceEnd;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.reservationStatus = reservationStatus;
    }

    public static List<ServiceReturnDto> toDtoList(List<Services> entities) {
        return entities.stream().map(
                (entity -> ServiceReturnDto.builder().entity(entity).build())
        ).collect(Collectors.toList());
    }

}
