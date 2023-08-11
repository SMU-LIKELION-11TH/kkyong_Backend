package smu.likelion.kkyong.service;

import smu.likelion.kkyong.domain.entity.users.Users;
import smu.likelion.kkyong.dto.Users.UsersModificationRequestDto;
import smu.likelion.kkyong.dto.Users.UsersRequestDto;
import smu.likelion.kkyong.dto.Users.UsersReturnDto;

public interface UsersService {

    public UsersReturnDto save(UsersRequestDto usersRequestDto);
    public Users update(Users users, UsersModificationRequestDto usersModificationRequestDto);
    public UsersReturnDto getUser(Users users);
    public Users findByEmail(String email);
}
