package smu.likelion.kkyong.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smu.likelion.kkyong.domain.entity.Users;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String nickname;
    private String phoneNumber;
    private String region;

    public Users toEntity() {
        return Users.builder()
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .region(this.region)
                .build();
    }

}
