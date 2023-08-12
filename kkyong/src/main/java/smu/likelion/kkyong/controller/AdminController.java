package smu.likelion.kkyong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.likelion.kkyong.api.PublicServiceAPI;
import smu.likelion.kkyong.domain.enums.Code;
import smu.likelion.kkyong.dto.common.ReturnDto;
import smu.likelion.kkyong.service.ServiceService;

@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor
public class AdminController {
    private final PublicServiceAPI publicServiceAPI;
    private final ServiceService serviceService;

    @GetMapping("/services")
    public String createService() {
        try {
            return publicServiceAPI.createAllService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/services/{serviceId}")
    public ResponseEntity<ReturnDto> deleteService(@PathVariable Long serviceId) {
        try {
            serviceService.deleteService(serviceId);
            return ResponseEntity.ok(ReturnDto.of(Code.OK));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
