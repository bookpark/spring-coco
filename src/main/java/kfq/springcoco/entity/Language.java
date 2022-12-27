package kfq.springcoco.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer languageId;

    @Enumerated(EnumType.STRING)
    private LanguageEnum language;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "member_id")
    private Member member;

}
