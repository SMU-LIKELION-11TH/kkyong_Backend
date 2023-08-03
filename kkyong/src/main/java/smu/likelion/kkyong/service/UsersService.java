package smu.likelion.kkyong.service;

import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.dto.UsersModificationRequestDto;
import smu.likelion.kkyong.dto.UsersRequestDto;
import smu.likelion.kkyong.dto.UsersReturnDto;

public interface UsersService {

    public UsersReturnDto save(UsersRequestDto usersRequestDto);
    public Users update(Users users, UsersModificationRequestDto usersModificationRequestDto);
    public UsersReturnDto getUser(Users users);
}
