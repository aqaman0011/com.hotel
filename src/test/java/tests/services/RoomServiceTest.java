package tests.services;

import models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister;
import repositories.RoomRepository;
import services.RoomService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    @Test
    public void testGetAllRooms() {
        // Arrange
        List<Room> expectedRooms = Collections.singletonList(new Room());

        RoomRepository roomRepositoryMock = mock(RoomRepository.class);
        when(roomRepositoryMock.findAll()).thenReturn(expectedRooms);

        RoomService roomService = new RoomService(roomRepositoryMock);

        // Act
        List<Room> actualRooms = roomService.getAllRooms();

        // Assert
        assertEquals(expectedRooms, actualRooms);
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
