package smu.likelion.kkyong.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import smu.likelion.kkyong.config.jwt.JwtTokenProvider;
import smu.likelion.kkyong.dto.user.TokenReturnDto;
import smu.likelion.kkyong.util.RedisService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class  OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider tokenProvider;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        TokenReturnDto token = tokenProvider.createToken(authentication);
        redisService.setValues(authentication.getName(), token.getRefreshToken());

        URI uri = UriComponentsBuilder
                .fromUriString("http://52.63.140.248/web/html/Main.html")
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .encode()
                .build()
                .toUri();

        getRedirectStrategy().sendRedirect(request, response, uri.toString());
    }
}
