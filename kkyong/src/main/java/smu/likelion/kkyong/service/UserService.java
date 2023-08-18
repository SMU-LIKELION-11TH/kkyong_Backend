package smu.likelion.kkyong.service;

import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.dto.user.*;

import java.util.List;

public interface UserService {

    public UserReturnDto register(RegisterRequestDto dto);
    public TokenReturnDto login(LoginRequestDto dto);
    public void logout(TokenRequestDto dto);
    public UserReturnDto getUser();
    public UserReturnDto updateUser(UserRequestDto dto);
    public void changePassword(PasswordRequestDto dto);
    public boolean existEmail(String email);
    public List<UserReturnDto> getUserList();
}
