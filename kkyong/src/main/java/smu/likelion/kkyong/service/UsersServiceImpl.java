package smu.likelion.kkyong.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import smu.likelion.kkyong.domain.entity.users.CustomUserDetail;
import smu.likelion.kkyong.domain.entity.users.Users;
import smu.likelion.kkyong.dto.Users.*;
import smu.likelion.kkyong.jwt.JwtTokenProvider;
import smu.likelion.kkyong.repository.UsersRepository;
import smu.likelion.kkyong.util.ExceptionUtil;

import javax.transaction.Transactional;

@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final UsersRepository usersRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private  final AuthenticationManager authenticationManager;

    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users users = usersRepository.findById(userId).orElseThrow(() -> ExceptionUtil.id(userId, Users.class.getName()));
        return new CustomUserDetail(users);
    }

    @Transactional
    public LoginReturnDto login(LoginRequestDto dto) {

        Users users = findByEmail(dto.getEmail());
        // Password Check
        if (!passwordEncoder.matches(dto.getPassword(), users.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        // try Authenticate
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        return LoginReturnDto.builder()
                .users(users)
                .jwt(jwt)
                .build();
    }

    @Override
    public UsersReturnDto save(UsersRequestDto usersRequestDto) {
        Users users = Users.builder()
                .email(usersRequestDto.getEmail())
                .nickname(usersRequestDto.getNickname())
                .password(usersRequestDto.getPassword())
                .kakaoId(usersRequestDto.getKakaoId())
                .phoneNumber(usersRequestDto.getPhoneNumber())
                .region(usersRequestDto.getRegion())
                .role(usersRequestDto.getRole())
                .build();
        usersRepository.save(users);
        return new UsersReturnDto(users);
    }

    @Override
    @Transactional
    public Users update(Users users, UsersModificationRequestDto usersModificationRequestDto) {
        String nickName = users.getNickname(), password = users.getPassword(), kakaoId = users.getKakaoId(), phoneNumber = users.getPhoneNumber(), region = users.getRegion();
        if (isValid(usersModificationRequestDto.getNickname())) nickName = usersModificationRequestDto.getNickname();
        if (isValid(usersModificationRequestDto.getPassword())) password = usersModificationRequestDto.getPassword();
        if (isValid(usersModificationRequestDto.getKakaoId())) kakaoId = usersModificationRequestDto.getKakaoId();
        if (isValid(usersModificationRequestDto.getPhoneNumber())) phoneNumber = usersModificationRequestDto.getPhoneNumber();
        if (isValid(usersModificationRequestDto.getRegion())) region = usersModificationRequestDto.getRegion();

        users.setUsersInfo(nickName, password, kakaoId, phoneNumber, region);
        usersRepository.save(users);
        return users;
    }

    @Override
    public UsersReturnDto getUser(Users users) {
        return new UsersReturnDto(users);
    }

    @Override
    public Users findByEmail(String email) {
        Users users = usersRepository.findByEmail(email).orElseThrow(() -> ExceptionUtil.id(email, Users.class.getName()));
        return users;
    }

    private boolean isValid(String input) {
        return input != null && !input.isEmpty();
    }
}
