package smu.likelion.kkyong.config.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Builder
@AllArgsConstructor
public class OAuthUser implements OAuth2User {

    private String registrationId;
    private Map<String, Object> attributes;
    private String role; // ROLE_USER
    private String email;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String getName() {
        return this.registrationId;
    }

    public String getEmail() {
        return this.email;
    }
}
