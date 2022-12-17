package kfq.springcoco.service;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.repository.AnswerRepository;
import kfq.springcoco.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public void createAnswer(String content, Question question, Member author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreatedTime(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAnswerAuthor(author);
        this.answerRepository.save(answer);
    }

    public List<Answer> answerList() {
        return this.answerRepository.findAll(Sort.by(Sort.Direction.DESC, "answerId"));
    }
}
