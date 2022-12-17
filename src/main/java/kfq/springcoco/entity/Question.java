package kfq.springcoco.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @Column(length = 200, nullable = false)
    @NotBlank
    private String title;

    @Column(length = 2000, nullable = false)
    @NotBlank
    private String content;

    @Column
    private LocalDateTime createdTime;

    @Column
    private LocalDateTime modifiedTime;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Answer> answerList;

    // ByteBuddyInterceptor 오류로 인해 QuestionList를 가져오지 못하는 문제를 해결하기 위해 EAGER로 변경
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "member_id")
    private Member questionAuthor;

}
