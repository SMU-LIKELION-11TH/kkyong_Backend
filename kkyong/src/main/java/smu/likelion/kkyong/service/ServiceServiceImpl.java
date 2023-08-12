package smu.likelion.kkyong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.likelion.kkyong.domain.entity.Bookmark;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.domain.enums.ServiceType;
import smu.likelion.kkyong.dto.service.ServiceRequestDto;
import smu.likelion.kkyong.dto.service.ServiceReturnDto;
import smu.likelion.kkyong.repository.BookmarkRepository;
import smu.likelion.kkyong.repository.ServiceRepository;
import smu.likelion.kkyong.repository.UserRepository;
import smu.likelion.kkyong.util.ExceptionUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    private Services findService(Long serviceId) {
        return serviceRepository.findById(serviceId).orElseThrow(
                () -> ExceptionUtil.id(serviceId, Services.class.getName())
        );
    }

    @Transactional
    @Override
    public List<ServiceReturnDto> getServiceListByRegion(ServiceType type, String region) {

        List<Services> services = serviceRepository.findByServiceTypeAndRegion(type, region);
        return Services.toDtoList(services);
    }

    @Transactional
    @Override
    public List<ServiceReturnDto> getServiceListByName(String serviceName) {
        List<Services> services = serviceRepository.findByServiceName(serviceName);
        return Services.toDtoList(services);
    }

    @Transactional
    @Override
    public ServiceReturnDto getService(Long serviceId) {
        Services service = findService(serviceId);

        // Users user = userRepository();

        return ServiceReturnDto.builder()
                .entity(service)
                //.bookmarkStatus(bookmarkRepository.existsByServiceAndUser(service, user))
                .build();
    }

    @Transactional
    @Override
    public void deleteService(Long serviceId) {
        Services service = findService(serviceId);
        serviceRepository.delete(service);
    }
}
