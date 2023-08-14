package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.enums.ServiceType;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Services, Long> {
    public List<Services> findByServiceTypeAndRegion(ServiceType type, String region);
    public List<Services> findByServiceNameContaining(String serviceName);
}
