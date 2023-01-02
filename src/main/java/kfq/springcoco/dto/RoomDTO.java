package kfq.springcoco.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class RoomDTO {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static RoomDTO create(String name) {
        RoomDTO room = new RoomDTO();
        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        return room;
    }
}
