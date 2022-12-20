package kfq.springcoco.controller;

import kfq.springcoco.entity.Member;
import kfq.springcoco.service.CocoService;
import kfq.springcoco.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CocoController {

    private final CocoService cocoService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/api/cocos")
    public ResponseEntity<String> createCoco(@RequestParam String title,
                                             @RequestParam String content,
                                             @RequestParam String id) {
        ResponseEntity<String> res = null;
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Member member = (Member) customUserDetailsService.loadUserByUsername(id);
                cocoService.createCoco(title, content, member);
                res = new ResponseEntity<String>("코코 등록 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>("코코 등록 실패", HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

}
