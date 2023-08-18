package smu.likelion.kkyong.dto.user;

import lombok.*;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.domain.enums.Role;
@Data
public class RegisterRequestDto {

    private String email;
    private Role role;
    private String nickname;
    private String password;
    private String kakaoId;
    private String phoneNumber;
    private String region;


    public Users toEntity() {
        return Users.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .phoneNumber(this.phoneNumber)
                .region(this.region)
                .role(this.role)
                .build();
    }
}
