package kfq.springcoco.controller;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Language;
import kfq.springcoco.entity.LanguageEnum;
import kfq.springcoco.entity.Member;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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
                languageService.addLanguage(LanguageEnum.valueOf(language), member);
                res = new ResponseEntity<String>("언어 추가 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>("언어 추가 실패", HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

    // 멤버의 언어 리스트
    @GetMapping("/api/languages")
    public ResponseEntity<List<Language>> languageByMember(String id) throws Exception {
        ResponseEntity<List<Language>> res = null;
        List<Language> languages = null;
        try {
            Member member = (Member) customUserDetailsService.loadUserByUsername(id);
            languages = member.getLanguageList();
            res = new ResponseEntity<List<Language>>(languages, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Language>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    // 언어 Enum 리스트 보내기
    @GetMapping("/api/languages/list")
    public ResponseEntity<List<String>> languageList() {
        ResponseEntity<List<String>> res = null;
        List<String> languages = null;
        languages = languageService.languageEnums();
        res = new ResponseEntity<List<String>>(languages, HttpStatus.OK);
        return res;
    }
}
