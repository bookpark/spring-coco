package kfq.springcoco.repository;

import kfq.springcoco.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findByTitle(String title);

    Question findByTitleAndContent(String title, String content);

    List<Question> findByTitleLike(String title);
}
