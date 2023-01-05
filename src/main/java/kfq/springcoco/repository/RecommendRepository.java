package kfq.springcoco.repository;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Integer> {
    Recommend findByAnswerAndMember(Answer answer, Member member);

    @Query(value = "select count(*) from Recommend where answer_id = :answer_id", nativeQuery = true)
    public Integer recommendCount(@Param("answer_id") Integer answer_id);

}
