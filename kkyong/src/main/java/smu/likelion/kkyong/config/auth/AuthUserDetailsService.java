package smu.likelion.kkyong.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import smu.likelion.kkyong.config.PrincipalDetails;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.repository.UserRepository;
import smu.likelion.kkyong.util.ExceptionUtil;

@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(username).orElseThrow(
                () -> ExceptionUtil.id(username, Users.class.getName())
        );

        return PrincipalDetails.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().getName())
                .build();
    }
}
