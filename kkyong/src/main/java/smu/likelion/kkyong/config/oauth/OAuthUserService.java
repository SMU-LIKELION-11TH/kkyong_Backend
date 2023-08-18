package smu.likelion.kkyong.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import smu.likelion.kkyong.config.PrincipalDetails;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        /*
        * OAuth2UserRequet : 사용자 인증을 마친 최종 Access Token 보유
        */
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        Map<String, Object> originAttributes = oAuth2User.getAttributes();

        // KAKAO 정보
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes oAuthAttributes = OAuthAttributes.ofKakao("id", originAttributes);
        Users user = saveOrUpdate(oAuthAttributes);

        return PrincipalDetails.builder()
                .registrationId(registrationId)
                .attributes(originAttributes)
                .username(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }

    private Users saveOrUpdate(OAuthAttributes authAttributes) {
        Users user = userRepository.findByEmail(authAttributes.getEmail() + "kakao")
                .map(entity -> entity.updateOAuth(authAttributes.getNickname()))
                .orElse(authAttributes.toEntity(authAttributes.getEmail() + "kakao"));

        return userRepository.save(user);
    }

}
