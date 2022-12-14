package kfq.springcoco.service;

import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void createQuestion(String title, String content,
                               List<String> languagesList,
                               List<String> skillList,
                               Member author) {
        Question q = new Question();
        q.setTitle(title);
        q.setContent(content);
        q.setCreatedTime(LocalDateTime.now());
        q.setLanguageList(languagesList);
        q.setSkillList(skillList);
        q.setQuestionAuthor(author);
        this.questionRepository.save(q);
    }

    public void modifyQuestion(Question question, String title, String content) {
        question.setTitle(title);
        question.setContent(content);
        question.setModifiedTime(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public List<Question> questionList() {
        return this.questionRepository.findAll(Sort.by(Sort.Direction.DESC, "questionId"));
    }

    public List<Question> questionListByView() {
        return this.questionRepository.findAll(Sort.by(Sort.Direction.DESC, "viewCount"));
    }

    public Question getQuestion(Integer questionId) throws Exception {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()) {
            return question.get();
        }
        throw new Exception("질문 글 오류");
    }

    // 질문 삭제
    public void deleteQuestion(Question question) {
        this.questionRepository.delete(question);
    }

    // 질문 검색 (제목)
    @Transactional(readOnly = true)
    public List<Question> searchTitle(String keyword) {
        return questionRepository
                .findByTitleContainingIgnoreCase(keyword);
    }

    // 질문 검색 (제목+내용)
    @Transactional(readOnly = true)
    public List<Question> searchAll(String keyword) {
        return questionRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
    }

}