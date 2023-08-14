package smu.likelion.kkyong.service;

import smu.likelion.kkyong.dto.reservation.ReservationReturnDto;
import smu.likelion.kkyong.dto.reservation.ReservationRequestDto;
import smu.likelion.kkyong.dto.reservation.TimeDto;

import java.util.List;

public interface ReservationService {

    public ReservationReturnDto createReservation(Long serviceId, ReservationRequestDto dto);
    public List<ReservationReturnDto> getReservationList();
    public ReservationReturnDto getReservation(String reservationNumber);
    public List<TimeDto> getReservationTime(Long serviceId, String reservationDate);
    public void deleteReservation(String reservationNumber);
}
