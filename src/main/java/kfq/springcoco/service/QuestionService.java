package kfq.springcoco.service;

import kfq.springcoco.domain.Question;
import kfq.springcoco.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}