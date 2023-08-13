package smu.likelion.kkyong.service;

import lombok.RequiredArgsConstructor;
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
import smu.likelion.kkyong.dto.user.TokenReturnDto;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {

    private final RestTemplate rt;

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
                KakaoAccessToken.class //반환되는 데이터타입
        );

        return response.getBody();
    }

}
