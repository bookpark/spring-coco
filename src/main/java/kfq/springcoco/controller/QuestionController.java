package kfq.springcoco.controller;

import kfq.springcoco.dto.QuestionDTO;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.repository.QuestionRepository;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final CustomUserDetailsService customUserDetailsService;
    private final QuestionRepository questionRepository;

    // 질문 작성
    @PostMapping("/api/questions")
    public ResponseEntity<String> createQuestion(@Valid QuestionDTO questionDTO,
                                                 String id) {
        ResponseEntity<String> res = null;
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Member member = (Member) customUserDetailsService.loadUserByUsername(id);
                Question q = new Question();
                q.setTitle(questionDTO.getTitle());
                q.setContent(questionDTO.getContent());
                q.setLanguageList(questionDTO.getLanguageList());
                q.setSkillList(questionDTO.getSkillList());
                q.setCreatedTime(LocalDateTime.now());
                q.setQuestionAuthor(member);
                questionRepository.save(q);
//                questionService.createQuestion(title, content, languageList, skillList, member);
                res = new ResponseEntity<String>("질문 작성 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>("질문 작성 실패", HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

    // 질문 전체 리스트
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

    // 질문 수정
    @PutMapping("/api/questions/{questionId}")
    public ResponseEntity<String> modifyQuestion(String title,
                                                 String content,
                                                 String id,
                                                 @PathVariable Integer questionId) {
        ResponseEntity<String> res = null;
        Member member = (Member) customUserDetailsService.loadUserByUsername(id);
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else if (id.equals(member.getEmail())) {
            try {
                Question question = questionService.getQuestion(questionId);
                questionService.modifyQuestion(question, title, content);
                res = new ResponseEntity<String>("답변 수정 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>("답변 수정 실패", HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

    // 질문별 답변 리스트
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

    // 질문 삭제
    @DeleteMapping("/api/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer questionId) throws Exception {
        ResponseEntity<String> res = null;
        try {
            Question question = questionService.getQuestion(questionId);
            questionService.deleteQuestion(question);
            res = new ResponseEntity<String>("질문이 성공적으로 삭제되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("질문 삭제 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

//    @GetMapping('/api/read/{id}')
//    public String read(@PathVariable Integer id, Model model){
//        QuestionDTO dto = questionService.getQuestion(id);
//        model.addAttribute('posts', dto);
//    }
}
