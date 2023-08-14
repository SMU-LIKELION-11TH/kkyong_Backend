package smu.likelion.kkyong.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import smu.likelion.kkyong.dto.oauth.KakaoAccessToken;
import smu.likelion.kkyong.dto.oauth.KakaoUser;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {

    private final RestTemplate rt;
    private final UserService service;

    @Value("${KAKAO-APIKEY}")
    private String kakaoApiKey;

    @Value("${KAKAO-REDIRECT-URL")
    private String kakaoRedirectUrl;

    @Transactional
    @Override
    public String loginKakao(String code) {
        System.out.println("code = " + code);
        // KAKAO Login 취소 시, 처리

        KakaoAccessToken accessToken = getKakaoAccessToken(code);
        System.out.println("accessToken = " + accessToken);

        KakaoUser kakaoUser = getKakaoUser(accessToken);
        System.out.println("kakaoUser = " + kakaoUser);



        return "test";
    }

    private KakaoAccessToken getKakaoAccessToken(String code) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP BODY
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoApiKey);
        body.add("redirect_url", kakaoRedirectUrl);
        body.add("code", code);

        // HTTP Entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        // HTTP POST Request
        ResponseEntity<KakaoAccessToken> response = rt.exchange(
                uri,
                HttpMethod.POST,
                entity,
                KakaoAccessToken.class
        );

        return response.getBody();
    }

    public KakaoUser getKakaoUser(KakaoAccessToken accessToken) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken.getToken_type() + " " + accessToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP BODY
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        // HTTP Entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        // HTTP POST Request
        ResponseEntity<String> response = rt.exchange(
                uri,
                HttpMethod.POST,
                entity,
                String.class
        );
        JsonElement result = JsonParser.parseString(response.getBody());
        JsonElement kakaoAccount = result.getAsJsonObject().get("kakao_account");
        JsonElement profile = kakaoAccount.getAsJsonObject().get("profile");

        return KakaoUser.builder()
                .email(kakaoAccount.getAsJsonObject().get("email").getAsString())
                .nickname(profile.getAsJsonObject().get("nickname").getAsString())
                .build();
    }

}
