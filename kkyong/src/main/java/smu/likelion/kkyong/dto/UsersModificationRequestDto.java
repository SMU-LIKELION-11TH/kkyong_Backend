package smu.likelion.kkyong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smu.likelion.kkyong.domain.entity.Users;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersModificationRequestDto {

    private String nickname;

    private String password;

    private String kakaoId;

    private String phoneNumber;

    private String region;

    public Users toEntity() {
        return Users.builder()
                .nickname(this.nickname)
                .password(this.password)
                .kakaoId(this.kakaoId)
                .phoneNumber(this.phoneNumber)
                .region(this.region)
                .build();
    }

}
