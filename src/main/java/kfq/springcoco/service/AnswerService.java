package kfq.springcoco.service;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.repository.AnswerRepository;
import kfq.springcoco.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer createAnswer(String content, Question question, Member author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreatedTime(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAnswerAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer answerId) throws Exception {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (answer.isPresent()) {
            return answer.get();
        }
        throw new Exception("답변 글 오류");
    }

    public void modifyAnswer(String content, Answer answer, Member author) {
        answer.setContent(content);
        answer.setModifiedTime(LocalDateTime.now());
        answer.setAnswerAuthor(author);
        this.answerRepository.save(answer);
    }

    public void deleteAnswer(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public List<Answer> answerList() {
        return this.answerRepository.findAll(Sort.by(Sort.Direction.DESC, "answerId"));
    }
}
