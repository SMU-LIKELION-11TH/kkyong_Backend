package smu.likelion.kkyong.dto.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smu.likelion.kkyong.domain.entity.users.Users;
import smu.likelion.kkyong.domain.enums.Role;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequestDto {

    private String email;

    private String nickname;

    private String password;

    private String kakaoId;

    private String phoneNumber;

    private String region;

    private Role role;

    public Users toEntity() {
        return Users.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .kakaoId(this.kakaoId)
                .phoneNumber(this.phoneNumber)
                .region(this.region)
                .role(this.role)
                .build();
    }
}
