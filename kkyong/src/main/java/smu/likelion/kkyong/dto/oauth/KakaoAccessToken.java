package smu.likelion.kkyong.dto.oauth;

import lombok.Builder;
import lombok.Data;

@Data
public class KakaoAccessToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String refresh_token_expires_in;

    @Builder
    public KakaoAccessToken(String access_token, String token_type, String refresh_token, String expires_in,
                            String refresh_token_expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.refresh_token_expires_in = refresh_token_expires_in;
    }
}
