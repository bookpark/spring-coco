package kfq.springcoco.service;

import kfq.springcoco.domain.Question;
import kfq.springcoco.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

}