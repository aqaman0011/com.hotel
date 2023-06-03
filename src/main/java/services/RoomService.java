package services;


import models.Room;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import repositories.RoomRepository;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long roomId) throws ChangeSetPersister.NotFoundException {
        return roomRepository.findById(roomId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Long roomId) throws ChangeSetPersister.NotFoundException {
        Room room = getRoomById(roomId);
        roomRepository.delete(room);
    }

    public Room updateRoom(Room updatedRoom) {
        return roomRepository.save(updatedRoom);
    }


}
