package smu.likelion.kkyong.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_number", nullable = false)
    private String reservationNumber;

    @Column(name = "reservation_date", nullable = false)
    private String reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    @Builder
    public Reservation(String reservationNumber, String reservationDate, Users user, Service service) {
        this.reservationNumber = reservationNumber;
        this.reservationDate = reservationDate;
        this.user = user;
        this.service = service;
    }
}
