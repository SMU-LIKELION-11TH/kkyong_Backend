package smu.likelion.kkyong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.likelion.kkyong.domain.enums.Code;
import smu.likelion.kkyong.domain.enums.ServiceType;
import smu.likelion.kkyong.dto.common.ReturnDto;
import smu.likelion.kkyong.service.BookmarkService;
import smu.likelion.kkyong.service.ServiceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final BookmarkService bookmarkService;

    @GetMapping("/{serviceId}")
    public ResponseEntity<ReturnDto> getService(@PathVariable Long serviceId) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, serviceService.getService(serviceId)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/")
    public ResponseEntity<ReturnDto> getServiceListByRegion(@RequestParam ServiceType type, @RequestParam String region) {
        try {
          return ResponseEntity.ok(ReturnDto.of(Code.OK, serviceService.getServiceListByRegion(type, region)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<ReturnDto> getServiceListByName(@RequestParam String serviceName) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, serviceService.getServiceListByName(serviceName)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/bookmark")
    public ResponseEntity<ReturnDto> getMyBookmarkList() {
        try {
           return ResponseEntity.ok(ReturnDto.of(Code.OK, bookmarkService.getBookmarkList()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/{serviceId}/bookmark")
    public ResponseEntity<ReturnDto> createBookmark(@PathVariable Long serviceId) {
        try {
            bookmarkService.createBookmark(serviceId);
            return ResponseEntity.ok(ReturnDto.of(Code.OK));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @DeleteMapping("/{serviceId}/bookmark")
    public ResponseEntity<ReturnDto> deleteBookmark(@PathVariable Long serviceId) {
        try {
            bookmarkService.deleteBookmark(serviceId);
            return ResponseEntity.ok(ReturnDto.of(Code.OK));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
