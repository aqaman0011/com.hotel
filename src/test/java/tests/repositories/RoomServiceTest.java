package tests.repositories;


import models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister;
import repositories.RoomRepository;
import services.RoomService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    @Test
    public void testGetAllRooms() {
        // Arrange
        Room room1 = new Room();
        room1.setId(1L);
        Room room2 = new Room();
        room2.setId(2L);

        RoomRepository roomRepositoryMock = mock(RoomRepository.class);
        when(roomRepositoryMock.findAll()).thenReturn(Arrays.asList(room1, room2));

        RoomService roomService = new RoomService(roomRepositoryMock);

        // Act
        List<Room> rooms = roomService.getAllRooms();

        // Assert
        assertEquals(2, rooms.size());
        assertTrue(rooms.contains(room1));
        assertTrue(rooms.contains(room2));
    }

    @Test
    public void testGetRoomByIdExistingRoom() throws ChangeSetPersister.NotFoundException {
        // Arrange
        Long roomId = 1L;
        Room expectedRoom = new Room();
        expectedRoom.setId(roomId);

        RoomRepository roomRepositoryMock = mock(RoomRepository.class);
        when(roomRepositoryMock.findById(roomId)).thenReturn(Optional.of(expectedRoom));

        RoomService roomService = new RoomService(roomRepositoryMock);

        // Act
        Room actualRoom = roomService.getRoomById(roomId);

        // Assert
        assertEquals(expectedRoom, actualRoom);
    }

    @Test
    public void testGetRoomByIdNonExistingRoom() {
        // Arrange
        Long roomId = 1L;

        RoomRepository roomRepositoryMock = mock(RoomRepository.class);
        when(roomRepositoryMock.findById(roomId)).thenReturn(Optional.empty());

        RoomService roomService = new RoomService(roomRepositoryMock);

        // Act & Assert
        assertThrows(ChangeSetPersister.NotFoundException.class, () -> roomService.getRoomById(roomId));
    }


}


