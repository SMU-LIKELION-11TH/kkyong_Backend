package smu.likelion.kkyong.domain.enums;

public enum ServiceType {
    SPORT("체육"),
    SPACE("공간"),
    CULTURE("문화"),
    EDUCATION("교육"),
    MEDICAL("진료 복지");

    private String typeName;

    ServiceType(String name) {
        this.typeName = name;
    }
}
