package smu.likelion.kkyong.service;

import smu.likelion.kkyong.domain.enums.ServiceType;
import smu.likelion.kkyong.dto.service.ServiceRequestDto;
import smu.likelion.kkyong.dto.service.ServiceReturnDto;

import java.util.List;

public interface ServiceService {
    public List<ServiceReturnDto> getServiceListByRegion(ServiceType type, String region);
    public List<ServiceReturnDto> getServiceListByName(String serviceName);
    public ServiceReturnDto getService(Long serviceId);
    public void deleteService(Long serviceId);
    public List<ServiceReturnDto> getServiceList();
}
