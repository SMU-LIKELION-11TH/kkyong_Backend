package smu.likelion.kkyong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smu.likelion.kkyong.config.auth.AuthUtil;
import smu.likelion.kkyong.domain.enums.Code;
import smu.likelion.kkyong.dto.common.ReturnDto;
import smu.likelion.kkyong.dto.reservation.ReservationRequestDto;
import smu.likelion.kkyong.service.ReservationService;
import smu.likelion.kkyong.service.ReservationServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationServiceImpl reservationService;

    @GetMapping("/my")
    public ResponseEntity<ReturnDto> getMyReservationList() {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, reservationService.getReservationList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{reservationNumber}")
    public ResponseEntity<ReturnDto> getReservation(@PathVariable String reservationNumber) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, reservationService.getReservation(reservationNumber)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/{serviceId}")
    public ResponseEntity<ReturnDto> createReservation(@PathVariable Long serviceId,
                                                       @RequestBody ReservationRequestDto dto) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, reservationService.createReservation(serviceId, dto)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{serviceId}/time")
    public ResponseEntity<ReturnDto> getReservationTimeList(@PathVariable Long serviceId,
                                                            @RequestParam String date) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, reservationService.getReservationTime(serviceId, date)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{reservationNumber}")
    public ResponseEntity<ReturnDto> deleteReservation(@PathVariable String reservationNumber) {
        if(AuthUtil.getAuthUser().equals(reservationService.findReservation(reservationNumber).getUser().getEmail())) {
            try {
                reservationService.deleteReservation(reservationNumber);
                return ResponseEntity.ok(ReturnDto.of(Code.OK));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
