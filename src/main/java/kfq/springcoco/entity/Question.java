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

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @NotBlank
    private String content;

    @Column
    private LocalDateTime createdTime;

    @Column
    private LocalDateTime modifiedTime;

    @ElementCollection
    private List<String> languageList;

    @ElementCollection
    private List<String> skillList;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Answer> answerList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "member_id")
    private Member questionAuthor;

    // 조회수
    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer view;

}
