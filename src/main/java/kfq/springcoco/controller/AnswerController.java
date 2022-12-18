package kfq.springcoco.controller;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.service.AnswerService;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final CustomUserDetailsService customUserDetailsService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/api/questions/{questionId}/answers")
    public ResponseEntity<String> createAnswer(String content,
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

    @PutMapping("/api/answers/{answerId}")
    public ResponseEntity<String> modifyAnswer(String content,
                                               String id,
                                               @PathVariable Integer answerId) {
        ResponseEntity<String> res = null;
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Member member = (Member) customUserDetailsService.loadUserByUsername(id);
                Answer answer = answerService.getAnswer(answerId);
                answerService.modifyAnswer(content, answer, member);
                res = new ResponseEntity<String>("답변 수정 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>("답변 수정 실패", HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

    @GetMapping("/api/questions/{questionId}/answers")
    public ResponseEntity<List<Answer>> answerList(@PathVariable Integer questionId) throws Exception {
        ResponseEntity<List<Answer>> res = null;
        List<Answer> answers = null;
        try {
            Question question = questionService.getQuestion(questionId);
            answers = question.getAnswerList();
            res = new ResponseEntity<List<Answer>>(answers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Answer>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @DeleteMapping("/api/answers/delete")
    public ResponseEntity<?> deleteAnswer(Integer answerId) throws Exception {
        ResponseEntity<String> res = null;
        try {
            Answer answer = answerService.getAnswer(answerId);
            answerService.deleteAnswer(answer);
            res = new ResponseEntity<String>("답변이 성공적으로 삭제되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("답변 삭제 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

}
