package smu.likelion.kkyong.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("사용자"),
    ADMIN("관리자");
    private final String role;
}
