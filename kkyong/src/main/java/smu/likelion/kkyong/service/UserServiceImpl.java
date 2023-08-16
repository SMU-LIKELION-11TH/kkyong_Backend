package smu.likelion.kkyong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.likelion.kkyong.config.auth.AuthUtil;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.dto.user.*;
import smu.likelion.kkyong.config.jwt.JwtTokenProvider;
import smu.likelion.kkyong.repository.UserRepository;
import smu.likelion.kkyong.util.ExceptionUtil;
import smu.likelion.kkyong.util.RedisService;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;
    private final RedisService redisService;

    private Users findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> ExceptionUtil.id(email, Users.class.getName()));
    }


    @Transactional
    @Override
    public TokenReturnDto login(LoginRequestDto dto) {

        Users user = findUser(dto.getEmail());

        // Password Check
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        // try Authenticate
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        Authentication authentication = authenticationManager.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenReturnDto tokenDto = tokenProvider.createToken(authentication);
        redisService.setValues(user.getEmail(), tokenDto.getRefreshToken());

        return tokenDto; // JWT 반환
    }

    @Transactional
    @Override
    public void logout(TokenRequestDto dto) {
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!tokenProvider.validateToken(dto.getAccessToken())){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 User email을 가져온다
        Authentication authentication = tokenProvider.getAuthentication(dto.getAccessToken());

        // Redis에서 해당 User email로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisService.getValues(authentication.getName())!=null){
            // Refresh Token을 삭제
            redisService.deleteValues(authentication.getName());
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
        redisService.setValues(dto.getAccessToken(),"logout");
    }

    @Transactional
    @Override
    public UserReturnDto register(RegisterRequestDto dto) {
        Users user = dto.toEntity();
        user.updatePassword(passwordEncoder.encode(dto.getPassword()));
        return UserReturnDto.builder()
                .user(userRepository.save(user))
                .build();
    }

    @Transactional
    @Override
    public UserReturnDto updateUser(UserRequestDto dto) {
        Users user = findUser(AuthUtil.getAuthUser());
        user.updateInfo(dto.getNickname(), dto.getPhoneNumber(), dto.getRegion());

        return UserReturnDto.builder()
                .user(userRepository.save(user))
                .build();
    }
    @Transactional
    @Override
    public void changePassword(PasswordRequestDto dto) {
        Users user = findUser(AuthUtil.getAuthUser());

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        user.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserReturnDto getUser() {
        return UserReturnDto.builder()
                .user(findUser(AuthUtil.getAuthUser()))
                .build();
    }
}
