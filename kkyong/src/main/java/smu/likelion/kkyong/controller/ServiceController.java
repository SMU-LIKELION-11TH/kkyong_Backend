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

    @GetMapping("/{type}")
    public ResponseEntity<ReturnDto> getServiceListByRegion(@PathVariable ServiceType type, @RequestParam String region) {
        try {
          return ResponseEntity.ok(ReturnDto.of(Code.OK, serviceService.getServiceListByRegion(type, region)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/")
    public ResponseEntity<ReturnDto> getServiceListByName(@RequestParam String serviceName) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, serviceService.getServiceListByName(serviceName)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/bookmark/{userId}")
    public ResponseEntity<ReturnDto> getMyBookmarkList(@PathVariable Long userId) {
        try {
           return ResponseEntity.ok(ReturnDto.of(Code.OK, bookmarkService.getBookmarkList(userId)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/{serviceId}/bookmark/{userId}")
    public ResponseEntity<ReturnDto> createBookmark(@PathVariable Long serviceId,
                                                    @PathVariable Long userId) {
        try {
            bookmarkService.createBookmark(serviceId, userId);
            return ResponseEntity.ok(ReturnDto.of(Code.OK));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @DeleteMapping("/{serviceId}/bookmark/{userId}")
    public ResponseEntity<ReturnDto> deleteBookmark(@PathVariable Long serviceId,
                                                    @PathVariable Long userId) {
        try {
            bookmarkService.deleteBookmark(serviceId, userId);
            return ResponseEntity.ok(ReturnDto.of(Code.OK));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
