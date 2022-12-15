package kfq.springcoco.repository;

import kfq.springcoco.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
