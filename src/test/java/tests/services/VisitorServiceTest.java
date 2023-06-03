package tests.services;

import models.Visitor;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister;
import repositories.VisitorRepository;
import services.VisitorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VisitorServiceTest {

    @Test
    public void testGetAllVisitors() {
        // Arrange
        List<Visitor> expectedVisitors = Collections.singletonList(new Visitor());

        VisitorRepository visitorRepositoryMock = mock(VisitorRepository.class);
        when(visitorRepositoryMock.findAll()).thenReturn(expectedVisitors);

        VisitorService visitorService = new VisitorService(visitorRepositoryMock);

        // Act
        List<Visitor> actualVisitors = visitorService.getAllVisitors();

        // Assert
        assertEquals(expectedVisitors, actualVisitors);
    }

    @Test
    public void testGetVisitorByIdExistingVisitor() throws ChangeSetPersister.NotFoundException {
        // Arrange
        Long visitorId = 1L;
        Visitor expectedVisitor = new Visitor();
        expectedVisitor.setId(visitorId);

        VisitorRepository visitorRepositoryMock = mock(VisitorRepository.class);
        when(visitorRepositoryMock.findById(visitorId)).thenReturn(Optional.of(expectedVisitor));

        VisitorService visitorService = new VisitorService(visitorRepositoryMock);

        // Act
        Visitor actualVisitor = visitorService.getVisitorById(visitorId);

        // Assert
        assertEquals(expectedVisitor, actualVisitor);
    }

    @Test
    public void testGetVisitorByIdNonExistingVisitor() {
        // Arrange
        Long visitorId = 1L;

        VisitorRepository visitorRepositoryMock = mock(VisitorRepository.class);
        when(visitorRepositoryMock.findById(visitorId)).thenReturn(Optional.empty());

        VisitorService visitorService = new VisitorService(visitorRepositoryMock);

        // Act & Assert
        assertThrows(ChangeSetPersister.NotFoundException.class, () -> visitorService.getVisitorById(visitorId));
    }

}
