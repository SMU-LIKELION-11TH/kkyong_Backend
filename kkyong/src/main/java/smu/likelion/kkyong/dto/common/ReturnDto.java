package smu.likelion.kkyong.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import smu.likelion.kkyong.domain.enums.Code;

@Getter @Setter
@AllArgsConstructor
public class ReturnDto {
    private final Integer code;
    private final String httpStatus;
    private final String message;
    private Object data;

    public static ReturnDto of(Code code, Object data) {
        return new ReturnDto(code.getCode(), code.getHttpStatus(), code.getMessage(), data);
    }

    public static ReturnDto of(Code code) {
        return new ReturnDto(code.getCode(), code.getHttpStatus(), code.getMessage(), null);
    }
}
