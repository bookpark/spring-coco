package kfq.springcoco.dto;

import kfq.springcoco.entity.Member;
import lombok.Data;

@Data
public class ChatDTO {
    private Integer roomId;
    private Member chatMember;
    private String message;
}
