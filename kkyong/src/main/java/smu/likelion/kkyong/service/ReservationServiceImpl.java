package smu.likelion.kkyong.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.likelion.kkyong.config.auth.AuthUtil;
import smu.likelion.kkyong.domain.entity.Reservation;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.dto.reservation.ReservationReturnDto;
import smu.likelion.kkyong.dto.reservation.ReservationRequestDto;
import smu.likelion.kkyong.dto.reservation.TimeDto;
import smu.likelion.kkyong.repository.ReservationRepository;
import smu.likelion.kkyong.repository.ServiceRepository;
import smu.likelion.kkyong.repository.UserRepository;
import smu.likelion.kkyong.util.ExceptionUtil;
import smu.likelion.kkyong.util.ReservationBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final ReservationRepository reservationRepository;

    private Users findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> ExceptionUtil.id(email, Users.class.getName())
        );
    }

    private Services findService(Long serviceId) {
        return serviceRepository.findById(serviceId).orElseThrow(
                () -> ExceptionUtil.id(serviceId, Services.class.getName())
        );
    }

    private Reservation findReservation(String reservationNumber) {
        return reservationRepository.findByReservationNumber(reservationNumber).orElseThrow(
                () -> ExceptionUtil.id(reservationNumber, Services.class.getName())
        );
    }

    @Transactional
    @Override
    public ReservationReturnDto createReservation(Long serviceId, ReservationRequestDto dto) {
        Services service = findService(serviceId);
        Users user = findUser(AuthUtil.getAuthUser());

        Reservation reservation = Reservation.builder()
                .reservationDate(dto.getDate())
                .reservationNumber(ReservationBuilder.createReservationNumber(String.valueOf(serviceId)))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .service(service)
                .user(user)
                .build();

        log.info(user.getEmail() + " : create reservation " + serviceId);

        return ReservationReturnDto.builder()
                .reservation(reservationRepository.save(reservation))
                .service(service)
                .build();

    }

    @Transactional
    @Override
    public List<ReservationReturnDto> getReservationList() {
        Users user = findUser(AuthUtil.getAuthUser());

        List<Reservation> reservations = reservationRepository.findByUserOrderByReservationDateDesc(user);

        log.info(user.getEmail() + " : get reservation list");

        return reservations.stream().map(reservation -> ReservationReturnDto.builder()
                .reservation(reservation)
                .service(reservation.getService())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ReservationReturnDto getReservation(String reservationNumber) {
        Users user = findUser(AuthUtil.getAuthUser());
        Reservation reservation = findReservation(reservationNumber);

        log.info(user.getEmail() + " : get reservation " + reservationNumber);

        return ReservationReturnDto.builder()
                .reservation(reservation)
                .service(reservation.getService())
                .build();
    }

    @Transactional
    @Override
    public List<TimeDto> getReservationTime(Long serviceId, String reservationDate) {
        Users user = findUser(AuthUtil.getAuthUser());
        Services service = findService(serviceId);

        List<Reservation> reservations = reservationRepository.findByServiceAndReservationDate(service, reservationDate);

        log.info(user.getEmail() + " : get reservation time " + serviceId);

        return reservations.stream().map(reservation -> TimeDto.builder()
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteReservation(String reservationNumber) {
        Users user = findUser(AuthUtil.getAuthUser());
        Reservation reservation = findReservation(reservationNumber);

        if(!user.equals(reservation.getUser())) {
            throw ExceptionUtil.available("No Authorized");
        }

        log.info(user.getEmail() + " : delete reservation " + reservationNumber);

        reservationRepository.delete(reservation);
    }
}
