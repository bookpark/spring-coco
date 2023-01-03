package kfq.springcoco.controller;

import kfq.springcoco.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StompChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/enter")
    public void enter(ChatDTO message) {
        message.setMessage(message.getChatMember() + "님이 채팅방에 입장하였습니다.");
        simpMessagingTemplate.convertAndSend("/sub/chat/" + message.getRoomId(), message);
    }

    @MessageMapping("/chat/message")
    public void sendMessage(ChatDTO message, SimpMessageHeaderAccessor accessor) {
        simpMessagingTemplate.convertAndSend("/sub/chat/" + message.getRoomId(), message);
    }
}
