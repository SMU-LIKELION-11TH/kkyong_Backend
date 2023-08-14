package smu.likelion.kkyong.dto.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class KakaoUser {

    private String nickname;
    private String email;

    @Builder
    public KakaoUser(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
