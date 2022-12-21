package kfq.springcoco.controller;

import kfq.springcoco.entity.Member;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LanguageController {

    private final CustomUserDetailsService customUserDetailsService;
    private final LanguageService languageService;

    // 멤버에 언어 추가
    @PostMapping("/api/languages")
    public ResponseEntity<String> addLanguage(String language, String id) {
        ResponseEntity<String> res = null;
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Member member = (Member) customUserDetailsService.loadUserByUsername(id);
                languageService.addLanguage(language, member);
                res = new ResponseEntity<String>("언어 추가 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>("언어 추가 실패", HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }
}
