package smu.likelion.kkyong.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class PasswordRequestDto {
    private String newPassword;
    private String oldPassword;

    @Builder
    public PasswordRequestDto(String newPassword, String oldPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }
}
