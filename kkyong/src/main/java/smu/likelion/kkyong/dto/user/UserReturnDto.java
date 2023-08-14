package smu.likelion.kkyong.dto.user;

import lombok.Builder;
import lombok.Data;
import smu.likelion.kkyong.domain.entity.Users;

@Data
public class UserReturnDto {

    private Long id;
    private String email;
    private String nickname;
    private String kakaoId;
    private String phoneNumber;
    private String region;
    private String createdAt;
    private String updateddAt;

    @Builder
    public UserReturnDto(Users user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.kakaoId = user.getKakaoId();
        this.phoneNumber = user.getPhoneNumber();
        this.region = user.getRegion();
        this.createdAt = user.getCreatedAt();
        this.updateddAt = user.getUpdatedAt();
    }
}
