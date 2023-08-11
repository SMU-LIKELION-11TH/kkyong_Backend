package smu.likelion.kkyong.domain.entity.users;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@Getter
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
//    private String password;
//    private final String email; // email
//    private String role;
    private Users users;

    // 권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        return collection;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;   // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;   // 계정 잠김 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;   // 비밀번호 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return false;   // 사용자 활성화 여부
    }
}
