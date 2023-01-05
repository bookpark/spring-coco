package kfq.springcoco.repository;

import kfq.springcoco.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findAll();

    @Modifying
    @Transactional
    @Query(value = "update Answer set recommend_count = :recommendCount where answer_id = :answerId", nativeQuery = true)
    public void updateRecommendCount(@Param("recommendCount") Integer recommendCount,
                                     @Param("answerId") Integer answerId);

}
