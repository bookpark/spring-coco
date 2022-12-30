package kfq.springcoco.dto;

import kfq.springcoco.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionDTO {
    private String title;
    private String content;
    private LocalDateTime createdTime;
    private List<String> languageList;
    private List<String> skillList;
    private Member member;
}
