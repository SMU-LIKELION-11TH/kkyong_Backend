package smu.likelion.kkyong.config.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import smu.likelion.kkyong.config.auth.AuthUser;
import smu.likelion.kkyong.dto.user.TokenReturnDto;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@PropertySource(value = "application-API.properties")
public class JwtTokenProvider {

    private final String secretKey;
    private final String issuer;
    private final Long expirationMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                            @Value("${security.jwt.token.issuer}") String issuer,
                            @Value("${security.jwt.token.expire}") Long expirationMilliseconds){

        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.issuer = issuer;
        this.expirationMilliseconds = expirationMilliseconds;
    }

    // Authentication 객체의 권한 정보를 이용해서 토큰을 생성
    public TokenReturnDto createToken(Authentication authentication){

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + expirationMilliseconds);
        
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuer(issuer)
                .setIssuedAt(now)
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenReturnDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expireTime(accessTokenExpiresIn.getTime())
                .build();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token){
        try {
            parseClaims(token);
        } catch (MalformedJwtException e){
            log.info("Invalid Jwt Exception", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired Jwt Exception", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Exception", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return true;
    }

    public Authentication getAuthentication(String accessToken){

        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = AuthUser.builder()
                .password(accessToken)
                .username(claims.getSubject())
                .role(claims.get("auth").toString())
                .build();

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}


