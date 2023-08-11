package smu.likelion.kkyong.dto.user;

import lombok.*;

@Data
public class LoginRequestDto {
    private String email;
    private String password;

    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
