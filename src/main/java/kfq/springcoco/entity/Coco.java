package kfq.springcoco.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Coco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cocoId;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column
    @Nullable
    private Integer price;

    @Column
    private LocalDateTime createdTime;

    @Column
    private LocalDateTime modifiedTime;

    @ElementCollection
    private List<Object> languageList;

    @ElementCollection
    private List<Object> skillList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "member_id")
    private Member author;

    @Enumerated(EnumType.STRING)
    private CocoStatus status;

}
