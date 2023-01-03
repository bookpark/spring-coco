package kfq.springcoco.repository;

import kfq.springcoco.dto.RoomDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class RoomRepository {
    private Map<String, RoomDTO> roomDTOMap;

    @PostConstruct
    private void init() {
        roomDTOMap = new LinkedHashMap<>();
    }

    public List<RoomDTO> findAllRooms() {
        List<RoomDTO> rooms = new ArrayList<>(roomDTOMap.values());
        Collections.reverse(rooms);
        return rooms;
    }

    public RoomDTO findRoomById(String id) {
        return roomDTOMap.get(id);
    }

    public RoomDTO createRoomDTO(String name) {
        RoomDTO room = RoomDTO.create(name);
        roomDTOMap.put(room.getRoomId(), room);
        return room;
    }
}
