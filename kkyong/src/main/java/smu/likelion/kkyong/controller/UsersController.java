package smu.likelion.kkyong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import smu.likelion.kkyong.domain.entity.users.CustomUserDetail;
import smu.likelion.kkyong.domain.entity.users.Users;
import smu.likelion.kkyong.domain.enums.Code;
import smu.likelion.kkyong.dto.Users.LoginRequestDto;
import smu.likelion.kkyong.dto.Users.UsersModificationRequestDto;
import smu.likelion.kkyong.dto.Users.UsersRequestDto;
import smu.likelion.kkyong.dto.Users.UsersReturnDto;
import smu.likelion.kkyong.dto.common.ReturnDto;
import smu.likelion.kkyong.service.UsersServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsersController {

    private  final UsersServiceImpl usersService;
    private  final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register")
    public ResponseEntity<ReturnDto> register(@RequestBody UsersRequestDto usersRequestDto){
        usersRequestDto.setPassword(passwordEncoder.encode(usersRequestDto.getPassword()));
        return ResponseEntity.ok(ReturnDto.of(Code.OK, usersService.save(usersRequestDto)));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<ReturnDto> login(@RequestBody LoginRequestDto dto) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, usersService.login(dto)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<ReturnDto> getUsers(@AuthenticationPrincipal CustomUserDetail customUserDetail){
        UsersReturnDto usersReturnDto = usersService.getUser(customUserDetail.getUsers());
        return ResponseEntity.ok(ReturnDto.of(Code.OK, usersReturnDto));
    }

    @PutMapping(value = "/user")
    public ResponseEntity<ReturnDto> updateUsers(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestBody UsersModificationRequestDto usersRequestDto){
        Users user = usersService.update(customUserDetail.getUsers(), usersRequestDto);
        UsersReturnDto usersReturn = usersService.getUser(user);
        return ResponseEntity.ok(ReturnDto.of(Code.OK, usersReturn));
    }

}
