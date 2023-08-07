package smu.likelion.kkyong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.enums.ServiceType;
import smu.likelion.kkyong.dto.service.ServiceRequestDto;
import smu.likelion.kkyong.dto.service.ServiceReturnDto;
import smu.likelion.kkyong.repository.ServiceRepository;
import smu.likelion.kkyong.util.ExceptionUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    private Services findService(Long serviceId) {
        return serviceRepository.findById(serviceId).orElseThrow(
                () -> ExceptionUtil.id(serviceId, Services.class.getName())
        );
    }

    @Transactional
    @Override
    public List<ServiceReturnDto> getServiceListByRegion(ServiceType type, String region) {

        List<Services> services = serviceRepository.getServicesByServiceTypeAndRegion(type, region);
        return Services.toDtoList(services);
    }

    @Transactional
    @Override
    public List<ServiceReturnDto> getServiceListByName(String serviceName) {
        List<Services> services = serviceRepository.getServicesByServiceName(serviceName);
        return Services.toDtoList(services);
    }

    @Transactional
    @Override
    public ServiceReturnDto getService(Long serviceId) {
        Services service = findService(serviceId);
        return ServiceReturnDto.builder()
                .entity(service)
                .build();
    }

    @Transactional
    @Override
    public void deleteService(Long serviceId) {
        Services service = findService(serviceId);
        serviceRepository.delete(service);
    }
}
