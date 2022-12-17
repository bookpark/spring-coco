package kfq.springcoco.repository;

import kfq.springcoco.entity.Answer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Override
    @EntityGraph(attributePaths = {"question"})
    List<Answer> findAll();
}
