package kfq.springcoco.service;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void createAnswer(String content, Question question, Member author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreatedTime(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
    }
}
