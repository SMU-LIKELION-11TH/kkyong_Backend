package smu.likelion.kkyong.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.likelion.kkyong.config.auth.AuthUtil;
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
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    private Services findService(Long serviceId) {
        return serviceRepository.findById(serviceId).orElseThrow(
                () -> ExceptionUtil.id(serviceId, Services.class.getName())
        );
    }
    private Users findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> ExceptionUtil.id(email, Users.class.getName()));
    }

    @Transactional
    @Override
    public List<ServiceReturnDto> getServiceListByRegion(ServiceType type, String region) {
        Users user = findUser(AuthUtil.getAuthUser());

        log.info(user.getEmail() + " : get service list by region");

        List<Services> services = serviceRepository.findByServiceTypeAndRegion(type, region);
        return Services.toDtoList(services);
    }

    @Transactional
    @Override
    public List<ServiceReturnDto> getServiceListByName(String serviceName) {
        Users user = findUser(AuthUtil.getAuthUser());

        log.info(user.getEmail() + " : get service list by service name");

        List<Services> services = serviceRepository.findByServiceNameContaining(serviceName);
        return Services.toDtoList(services);
    }

    @Transactional
    @Override
    public ServiceReturnDto getService(Long serviceId) {
        Users user = findUser(AuthUtil.getAuthUser());
        Services service = findService(serviceId);

        log.info(user.getEmail() + " : get service " + serviceId);

        return ServiceReturnDto.builder()
                .entity(service)
                //.bookmarkStatus(bookmarkRepository.existsByServiceAndUser(service, user))
                .build();
    }

    @Transactional
    @Override
    public void deleteService(Long serviceId) {
        Users user = findUser(AuthUtil.getAuthUser());

        log.info(user.getEmail() + " : delete service " + serviceId);

        Services service = findService(serviceId);
        serviceRepository.delete(service);
    }

    @Transactional
    @Override
    public List<ServiceReturnDto> getServiceList() {
        List<Services> services = serviceRepository.findAll();
        return services.stream().map(service -> ServiceReturnDto.builder()
                .entity(service)
                .build()).collect(Collectors.toList());
    }
}
