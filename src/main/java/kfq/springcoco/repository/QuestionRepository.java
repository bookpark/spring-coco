package kfq.springcoco.repository;

import kfq.springcoco.entity.Question;
import kfq.springcoco.payload.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Override
    @Query(value = "select q from Question q")
    List<Question> findAll(Sort sort);

    // 조회수
//    @Modifying
//    @Query("update Question q set q.view = q.view + 1 where q.questionId =:id")
//    int updateView(Integer id);

    // 제목으로 검색 :  select like %title% from List<Question>, 대소문자 구별하지 않음
    List<Question> findByTitleContainingIgnoreCase (String title);
    // 내용으로 검색 : select like %content% from List<Question>, 대소문자 구별하지 않음
    List<Question> findByContentContainingIgnoreCase (String content);
    // 제목+내용으로 검색 : select like %title% || %content% from List<Question>, 대소문자 구별하지 않음
    List<Question> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase (String title, String content);

}
