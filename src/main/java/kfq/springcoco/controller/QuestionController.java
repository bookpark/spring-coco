package kfq.springcoco.controller;

import kfq.springcoco.entity.Question;
import kfq.springcoco.service.MemberService;
import kfq.springcoco.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @PostMapping("/api/questions")
    public ResponseEntity<String> createQuestion(@RequestParam String title,
                                                 @RequestParam String content) {
        ResponseEntity<String> res = null;
        try {
            questionService.createQuestion(title, content);
            res = new ResponseEntity<String>("질문 작성 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("질문 작성 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/api/questions")
    public ResponseEntity<List<Question>> questionList() throws Exception {
        ResponseEntity<List<Question>> res = null;
        List<Question> questions = null;
        try {
            questions = questionService.questionList();
            res = new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/api/questions/{questionId}")
    public ResponseEntity<Question> questionDetail(@PathVariable Integer questionId) throws Exception {
        ResponseEntity<Question> res = null;
        try {
            Question question = questionService.getQuestion(questionId);
            res = new ResponseEntity<Question>(question, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Question>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
}
