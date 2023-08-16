package smu.likelion.kkyong.config.oauth;

import lombok.Builder;
import lombok.Getter;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.domain.enums.Role;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes; // OAuth2 유저 정보
    private String email;
    private String nickname;
    private String nameAttributeKey;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String email, String nickname, String nameAttributeKey) {
        this.attributes = attributes;
        this.email = email;
        this.nickname = nickname;
        this.nameAttributeKey = nameAttributeKey;
    }

    public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .nickname(String.valueOf(kakaoProfile.get("nickname")))
                .email(String.valueOf(kakaoAccount.get("email")))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .email(this.email)
                .nickname(this.nickname)
                .role(Role.USER)
                .build();
    }
}
