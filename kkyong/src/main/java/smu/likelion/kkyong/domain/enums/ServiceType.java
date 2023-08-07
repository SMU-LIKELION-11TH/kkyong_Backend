package smu.likelion.kkyong.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ServiceType {
    SPORT("체육시설"),
    SPACE("공간시설"),
    CULTURE("문화체험"),
    EDUCATION("교육강좌"),
    MEDICAL("진료복지");

    private String typeName;

    ServiceType(String name) {
        this.typeName = name;
    }

    public static ServiceType findByName(String name) {
        return Arrays.stream(ServiceType.values())
                .filter(serviceType -> serviceType.getTypeName().equals(name))
                .findAny()
                .orElse(null);
    }
}
