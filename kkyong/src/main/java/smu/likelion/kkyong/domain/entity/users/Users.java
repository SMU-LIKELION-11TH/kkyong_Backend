package smu.likelion.kkyong.domain.entity.users;

import lombok.*;
import smu.likelion.kkyong.domain.entity.BaseEntity;
import smu.likelion.kkyong.domain.entity.Bookmark;
import smu.likelion.kkyong.domain.entity.Reservation;
import smu.likelion.kkyong.domain.enums.Role;
import smu.likelion.kkyong.dto.common.ReturnDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "users")
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="nickname", unique = true)
    private String nickname;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="kakao_id")
    private String kakaoId;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="region")
    private String region;

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

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
