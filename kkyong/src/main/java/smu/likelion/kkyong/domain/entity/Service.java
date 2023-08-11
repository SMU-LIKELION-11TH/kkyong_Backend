package smu.likelion.kkyong.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smu.likelion.kkyong.domain.enums.ServiceType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "services")
public class Service extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
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

    @Column
    private String xCoord;

    @Column
    private String yCoord;

    @Column(name = "service_start")
    private String serviceStart;

    @Column(name = "service_end")
    private String serviceEnd;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private String contact;

    @Column(name = "recruit_count")
    private Integer recruitCount;

    @Column(name = "reservation_status")
    private Boolean reservationStatus;

    @OneToMany(mappedBy = "service")
    private List<Reservation> reservations = new ArrayList<>();

    public Service(ServiceType serviceType, String serviceName, String serviceTarget, String region, String place,
                   String xCoord, String yCoord, String serviceStart, String serviceEnd, String imageUrl, String contact,
                   Integer recruitCount, Boolean reservationStatus) {
        this.serviceType = serviceType;
        this.serviceName = serviceName;
        this.serviceTarget = serviceTarget;
        this.region = region;
        this.place = place;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.serviceStart = serviceStart;
        this.serviceEnd = serviceEnd;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.recruitCount = recruitCount;
        this.reservationStatus = reservationStatus;
    }
}
