package kfq.springcoco.dto;

import kfq.springcoco.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerDTO {
    private String content;
    private LocalDateTime createdTime;
    private Member member;
}
