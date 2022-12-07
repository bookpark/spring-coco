package kfq.springcoco.service;

import kfq.springcoco.domain.Question;
import kfq.springcoco.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void createQuestion(String title, String content, Integer price) {
        Question q = new Question();
        q.setTitle(title);
        q.setContent(content);
        q.setPrice(price);
        this.questionRepository.save(q);
    }

    public List<Question> questionList() {
        return this.questionRepository.findAll(Sort.by(Sort.Direction.DESC, "question_id"));
    }

    public Question getQuestion(Integer id) throws Exception {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        }
        throw new Exception("질문 글 오류");
    }

}