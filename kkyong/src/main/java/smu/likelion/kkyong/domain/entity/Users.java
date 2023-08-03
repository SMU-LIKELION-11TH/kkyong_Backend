package smu.likelion.kkyong.domain.entity;

import lombok.*;
import org.hibernate.annotations.Entity;
import smu.likelion.kkyong.domain.enums.Role;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="nickname", unique = true)
    private String nickname;

    @Column(name="password")
    private String password;

    @Column(name="kakao_id")
    private String kakaoId;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="region")
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Builder
    public Users(String email, String nickname, String password, String kakaoId, String phoneNumber, String region, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.kakaoId = kakaoId;
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.role = role;
    }

    public void setUsersInfo(String nickname, String password, String kakaoId, String phoneNumber, String region){
        this.nickname = nickname;
        this.password = password;
        this.kakaoId = kakaoId;
        this.phoneNumber = phoneNumber;
        this.region = region;
    }
}
