package smu.likelion.kkyong.dto;

import smu.likelion.kkyong.domain.entity.Users;

public class UsersReturnDto {

    private String email;

    private String nickname;

    private String password;

    private String kakaoId;

    private String phoneNumber;

    private String region;

    private String createdAt;

    private String updateddAt;

    public UsersReturnDto(Users users) {
        this.email = users.getEmail();
        this.nickname = users.getNickname();
        this.password = users.getPassword();
        this.kakaoId = users.getKakaoId();
        this.phoneNumber = users.getPhoneNumber();
        this.region = users.getRegion();
        this.createdAt = users.getCreatedAt();
        this.updateddAt = users.getUpdatedAt();
    }
}
