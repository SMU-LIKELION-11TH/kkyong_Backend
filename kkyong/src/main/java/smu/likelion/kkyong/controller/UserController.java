package smu.likelion.kkyong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.likelion.kkyong.domain.enums.Code;
import smu.likelion.kkyong.dto.user.*;
import smu.likelion.kkyong.dto.common.ReturnDto;
import smu.likelion.kkyong.service.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<ReturnDto> register(@RequestBody RegisterRequestDto dto) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, userService.register(dto)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ReturnDto.of(Code.BAD_REQUEST));
    }

    @PostMapping("/login")
    public ResponseEntity<ReturnDto> login(@RequestBody LoginRequestDto dto) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, userService.login(dto)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ReturnDto.of(Code.BAD_REQUEST));
    }

    @PostMapping("/logout")
    public ResponseEntity<ReturnDto> logout(@RequestBody TokenRequestDto dto) {
        try {
            userService.logout(dto);
            return ResponseEntity.ok(ReturnDto.of(Code.OK));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(ReturnDto.of(Code.BAD_REQUEST));
    }

    @GetMapping( "/user")
    public ResponseEntity<ReturnDto> getUser() {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, userService.getUser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ReturnDto.of(Code.BAD_REQUEST));
    }

    @PutMapping("/user")
    public ResponseEntity<ReturnDto> updateUser(@RequestBody UserRequestDto dto) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, userService.updateUser(dto)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ReturnDto.of(Code.BAD_REQUEST));
    }

    @PutMapping("/user/password")
    public ResponseEntity<ReturnDto> changePassword(@RequestBody PasswordRequestDto dto) {
        try {
            userService.changePassword(dto);
            return ResponseEntity.ok(ReturnDto.of(Code.OK));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ReturnDto.of(Code.BAD_REQUEST));
    }
}
