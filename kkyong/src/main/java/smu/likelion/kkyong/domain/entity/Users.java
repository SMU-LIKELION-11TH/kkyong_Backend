package smu.likelion.kkyong.domain.entity;

import lombok.*;
import smu.likelion.kkyong.domain.enums.Role;

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

    private String password;
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
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.role = role;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
    public void updateInfo(String nickname, String phoneNumber, String region){
        this.nickname = nickname != null ? nickname : this.nickname;
        this.phoneNumber = phoneNumber != null ? phoneNumber : this.phoneNumber;
        this.region = region != null ? region : this.region;
    }

    public Users updateOAuth(String nickname) {
        this.nickname = nickname != null ? nickname : this.nickname;
        return this;
    }
}
