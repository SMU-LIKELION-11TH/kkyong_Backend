package smu.likelion.kkyong.dto.Users;

import lombok.Builder;
import smu.likelion.kkyong.domain.entity.users.Users;

public class LoginReturnDto {

    private String email;

    private String nickname;

    private String password;

    private String kakaoId;

    private String phoneNumber;

    private String region;

    private String createdAt;

    private String updateddAt;

    private String jwt;

    @Builder
    public LoginReturnDto(Users users, String jwt) {
        this.email = users.getEmail();
        this.nickname = users.getNickname();
        this.password = users.getPassword();
        this.kakaoId = users.getKakaoId();
        this.phoneNumber = users.getPhoneNumber();
        this.region = users.getRegion();
        this.createdAt = users.getCreatedAt();
        this.updateddAt = users.getUpdatedAt();
        this.jwt = jwt;
    }
}
