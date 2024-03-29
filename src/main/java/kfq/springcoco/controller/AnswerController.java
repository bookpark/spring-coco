package kfq.springcoco.controller;

import kfq.springcoco.dto.AnswerDTO;
import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.repository.AnswerRepository;
import kfq.springcoco.service.AnswerService;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.QuestionService;
import kfq.springcoco.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final CustomUserDetailsService customUserDetailsService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AnswerRepository answerRepository;

    @PostMapping("/api/questions/{questionId}/answers")
    public ResponseEntity<Answer> createAnswer(@Valid AnswerDTO answerDTO,
                                               String id,
                                               @PathVariable Integer questionId) {
        ResponseEntity<Answer> res = null;
        try {
            Member member = (Member) customUserDetailsService.loadUserByUsername(id);
            Question question = questionService.getQuestion(questionId);
            Answer a = new Answer();
            a.setContent(answerDTO.getContent());
            a.setCreatedTime(LocalDateTime.now());
            a.setAnswerAuthor(member);
            a.setQuestion(question);
            a.setRecommendCount(0);
            answerRepository.save(a);
            res = new ResponseEntity<Answer>(a, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Answer>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    // 답변 조회
    @GetMapping("/api/answers/{answerId}")
    public ResponseEntity<Answer> getAnswer(@PathVariable Integer answerId) {
        ResponseEntity<Answer> res = null;
        try {
            Answer answer = answerService.getAnswer(answerId);
            res = new ResponseEntity<Answer>(answer, HttpStatus.OK);
        } catch (Exception e) {
            res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    // 질문 상세의 답변 리스트
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

    // 질문 상세의 답변 인기순 리스트
    @GetMapping("/api/questions/{questionId}/answers/recommend")
    public ResponseEntity<List<Answer>> answerListByRecommend(@PathVariable Integer questionId) throws Exception {
        ResponseEntity<List<Answer>> res = null;
        List<Answer> answers = null;
        try {
            Question question = questionService.getQuestion(questionId);
            answers = question.getAnswerList();
//            answers.sort(Comparator.comparing(Answer::getRecommendCount));
            answers.sort(Collections.reverseOrder((a1, a2) ->
                    a1.getRecommendCount().compareTo(a2.getRecommendCount())));
            res = new ResponseEntity<List<Answer>>(answers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Answer>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @DeleteMapping("/api/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Integer answerId) throws Exception {
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
