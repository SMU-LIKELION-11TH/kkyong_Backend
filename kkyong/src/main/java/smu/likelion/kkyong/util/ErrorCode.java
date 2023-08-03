package smu.likelion.kkyong.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 성공 코드
     */
    OK(0, "요청이 정상적으로 수행되었습니다."),

    /**
     * 400 BAD REQUEST
     */
    BAD_REQUEST(400,"잘못된 요청입니다."),

    /**
     * 403 FORBIDDEN
     */
    FORBIDDEN(403, "권한이 없습니다.");

    private final int code;
    private final String message;
}
