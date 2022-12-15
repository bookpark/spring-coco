package kfq.springcoco.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Coco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cocoId;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @Column
    private LocalDateTime createdTime;

    @Column
    private LocalDateTime modifiedTime;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

}