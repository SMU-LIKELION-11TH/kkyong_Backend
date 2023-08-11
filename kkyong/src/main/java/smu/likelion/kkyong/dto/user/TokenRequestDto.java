package smu.likelion.kkyong.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenRequestDto {

    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenRequestDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}