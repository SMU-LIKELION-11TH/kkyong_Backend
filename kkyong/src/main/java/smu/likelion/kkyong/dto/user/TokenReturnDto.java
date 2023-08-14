package smu.likelion.kkyong.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenReturnDto {

    private String accessToken;
    private String refreshToken;
    private Long expireTime;

    @Builder
    public TokenReturnDto(String accessToken, String refreshToken, Long expireTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }
}
