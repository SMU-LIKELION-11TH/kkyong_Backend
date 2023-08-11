package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
