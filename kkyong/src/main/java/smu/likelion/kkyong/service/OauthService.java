package smu.likelion.kkyong.service;

import smu.likelion.kkyong.dto.user.TokenReturnDto;

public interface OauthService {
    public String loginKakao(String code);
}
