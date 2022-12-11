package kfq.springcoco.service;

import kfq.springcoco.domain.Question;
import kfq.springcoco.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void createQuestion(Question question) {
        this.questionRepository.save(question);
    }

    public List<Question> questionList() {
        return this.questionRepository.findAll(Sort.by(Sort.Direction.DESC, "questionId"));
    }

    public Question getQuestion(Integer questionId) throws Exception {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()) {
            return question.get();
        }
        throw new Exception("질문 글 오류");
    }

}