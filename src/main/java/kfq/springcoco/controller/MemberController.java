package kfq.springcoco.controller;

import kfq.springcoco.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<String> signUp(@RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         @RequestParam("nickname") String nickname
    ) {
        ResponseEntity<String> res = null;
        try {
            memberService.signUp(email, password, nickname);
            res = new ResponseEntity<String>("회원가입 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("회원가입 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

}
