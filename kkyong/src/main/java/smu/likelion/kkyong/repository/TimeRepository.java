package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {
}
