package kfq.springcoco.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    @Column(length = 2000)
    private String content;

    @Column
    private LocalDateTime createdTime;

    @Column
    private LocalDateTime modifiedTime;

    // ByteBuddyInterceptor 오류로 인해 AnswerList를 가져오지 못하는 문제를 해결하기 위해 EAGER로 변경
    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonManagedReference
    @JoinColumn(name = "question_id")
    private Question question;

    // ByteBuddyInterceptor 오류로 인해 AnswerList를 가져오지 못하는 문제를 해결하기 위해 EAGER로 변경
    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonManagedReference
    @JoinColumn(name = "member_id")
    private Member answerAuthor;

}
