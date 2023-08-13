package smu.likelion.kkyong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.likelion.kkyong.domain.enums.Code;
import smu.likelion.kkyong.dto.common.ReturnDto;
import smu.likelion.kkyong.service.OauthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/kakao")
    public ResponseEntity<ReturnDto> loginKakao(@RequestParam(required = false) String code,
                                                @RequestParam(required = false) String error) {
        try {
            return ResponseEntity.ok(ReturnDto.of(Code.OK, oauthService.loginKakao(code)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
