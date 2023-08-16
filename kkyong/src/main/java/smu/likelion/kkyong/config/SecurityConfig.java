package smu.likelion.kkyong.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import smu.likelion.kkyong.config.auth.AuthUserDetailsService;
import smu.likelion.kkyong.config.jwt.JwtAccessDeniedHandler;
import smu.likelion.kkyong.config.jwt.JwtAuthenticationEntryPoint;
import smu.likelion.kkyong.config.jwt.JwtFilter;
import smu.likelion.kkyong.config.jwt.JwtTokenProvider;
import smu.likelion.kkyong.config.oauth.OAuthSuccessHandler;
import smu.likelion.kkyong.config.oauth.OAuthUserService;
import smu.likelion.kkyong.repository.UserRepository;
import smu.likelion.kkyong.util.RedisService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity // Spring Security 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final OAuthUserService oAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    /**
     * permitAll : 인증, 권한 X 가능
     * authenticated : 인증 해야 됨
     * antMatchers("경로", "경로", ... ) 가능
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .headers()
                .frameOptions()
                .sameOrigin();
        http
                .oauth2Login()
                .authorizationEndpoint().baseUri("/api/oauth")
                .and()
                .redirectionEndpoint().baseUri("/api/oauth/callback/kakao")
                .and()
                .userInfoEndpoint().userService(oAuthUserService)
                .and()
                .successHandler(oAuthSuccessHandler);

        http
                .authorizeRequests()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/oauth/**").permitAll()
                .antMatchers("/admin/api").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtFilter(jwtTokenProvider, redisService), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthUserDetailsService(userRepository);
    }

    @Bean
    public OAuth2UserService oAuth2UserService() {
        return new OAuthUserService(userRepository);
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS", "PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}