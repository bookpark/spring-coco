package kfq.springcoco.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer question_id;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @Column
    private LocalDateTime created_time;

    @Column
    private LocalDateTime modified_time;

}
