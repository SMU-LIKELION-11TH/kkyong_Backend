package smu.likelion.kkyong.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class OAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {

        URI uri = UriComponentsBuilder.fromUriString("http://127.0.0.1:5501/oauth/redirect")
                .queryParam("error", authenticationException.getLocalizedMessage())
                .encode()
                .build()
                .toUri();

        getRedirectStrategy().sendRedirect(request, response, uri.toString());
    }
}
