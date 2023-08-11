package smu.likelion.kkyong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import smu.likelion.kkyong.domain.entity.users.CustomUserDetail;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.domain.enums.Code;
import smu.likelion.kkyong.dto.user.LoginRequestDto;
import smu.likelion.kkyong.dto.user.UserRequestDto;
import smu.likelion.kkyong.dto.user.RegisterRequestDto;
import smu.likelion.kkyong.dto.user.UserReturnDto;
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
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<ReturnDto> login(@RequestBody LoginRequestDto dto) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, userService.login(dto)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<ReturnDto> logout() {
        try {
            userService.logout();
            return ResponseEntity.ok(ReturnDto.of(Code.OK));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping( "/user")
    public ResponseEntity<ReturnDto> getUser() {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, userService.getUser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping(value = "/user")
    public ResponseEntity<ReturnDto> updateUser(@RequestBody UserRequestDto dto) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, userService.updateUser(dto)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
