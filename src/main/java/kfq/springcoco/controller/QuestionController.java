package kfq.springcoco.controller;

import kfq.springcoco.service.MemberService;
import kfq.springcoco.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @PostMapping("/api/questions")
    public ResponseEntity<String> createQuestion(@RequestParam("title") String title,
                                                 @RequestParam("content") String content,
                                                 @RequestParam("price") Integer price
    ) {
        ResponseEntity<String> res = null;
        try {
            questionService.createQuestion(title, content, price);
            res = new ResponseEntity<String>("질문 작성 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("질문 작성 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

}
