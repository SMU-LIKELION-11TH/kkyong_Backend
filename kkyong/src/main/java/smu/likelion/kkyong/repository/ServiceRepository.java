package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
