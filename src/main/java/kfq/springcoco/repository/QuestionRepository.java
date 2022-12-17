package kfq.springcoco.repository;

import kfq.springcoco.entity.Question;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Override
    @Query(value = "select q from Question q")
    List<Question> findAll(Sort sort);

}
