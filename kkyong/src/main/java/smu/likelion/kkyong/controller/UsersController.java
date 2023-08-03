package smu.likelion.kkyong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.dto.LoginRequestDto;
import smu.likelion.kkyong.dto.UsersModificationRequestDto;
import smu.likelion.kkyong.dto.UsersRequestDto;
import smu.likelion.kkyong.dto.UsersReturnDto;
import smu.likelion.kkyong.service.UsersServiceImpl;
import smu.likelion.kkyong.util.BaseResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsersController {

    private  final UsersServiceImpl usersService;
    private  final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register")
    public ResponseEntity<UsersReturnDto> register(@RequestBody UsersRequestDto usersRequestDto){
        usersRequestDto.setPassword(passwordEncoder.encode(usersRequestDto.getPassword()));
        return new ResponseEntity<>(usersService.save(usersRequestDto), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginRequestDto loginRequestDto){
        Users users = usersService.l
    }

    @GetMapping(value = "/user")
    public BaseResponse<UsersReturnDto> getUsers(@AuthenticationPrincipal Users users){
        UsersReturnDto usersReturnDto = usersService.getUser(users);
        return BaseResponse.ok(usersReturnDto);
    }

    @PutMapping(value = "/user")
    public BaseResponse<UsersReturnDto> updateUsers(@AuthenticationPrincipal Users users, @RequestBody UsersModificationRequestDto usersRequestDto){
        Users user = usersService.update(users, usersRequestDto);
        UsersReturnDto usersReturn = usersService.getUser(user);
        return BaseResponse.ok(usersReturn);
    }

}
