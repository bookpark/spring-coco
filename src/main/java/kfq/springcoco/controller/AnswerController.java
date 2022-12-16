package kfq.springcoco.controller;

import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.service.AnswerService;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final CustomUserDetailsService customUserDetailsService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/api/questions/{questionId}/answers")
    public ResponseEntity<String> createQuestion(String content,
                                                 String id,
                                                 @PathVariable Integer questionId) {
        ResponseEntity<String> res = null;
        try {
            Member member = (Member) customUserDetailsService.loadUserByUsername(id);
            Question question = questionService.getQuestion(questionId);
            System.out.println("답변: " + content);
            answerService.createAnswer(content, question, member);
            res = new ResponseEntity<String>("답변 작성 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("답변 작성 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

}
