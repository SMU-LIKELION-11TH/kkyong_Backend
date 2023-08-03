package smu.likelion.kkyong.service;

import org.springframework.stereotype.Service;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.dto.UsersModificationRequestDto;
import smu.likelion.kkyong.dto.UsersRequestDto;
import smu.likelion.kkyong.dto.UsersReturnDto;
import smu.likelion.kkyong.repository.UsersRepository;

import javax.transaction.Transactional;

@Service
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
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

    private boolean isValid(String input) {
        return input != null && !input.isEmpty();
    }
}
