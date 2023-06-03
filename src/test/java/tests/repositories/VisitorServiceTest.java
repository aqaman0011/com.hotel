package tests.repositories;

import models.Visitor;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister;
import repositories.VisitorRepository;
import services.VisitorService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VisitorServiceTest {

    @Test
    public void testGetAllVisitors() {
        // Arrange
        Visitor visitor1 = new Visitor();
        visitor1.setId(1L);
        Visitor visitor2 = new Visitor();
        visitor2.setId(2L);

        VisitorRepository visitorRepositoryMock = mock(VisitorRepository.class);
        when(visitorRepositoryMock.findAll()).thenReturn(Arrays.asList(visitor1, visitor2));

        VisitorService visitorService = new VisitorService(visitorRepositoryMock);

        // Act
        List<Visitor> visitors = visitorService.getAllVisitors();

        // Assert
        assertEquals(2, visitors.size());
        assertTrue(visitors.contains(visitor1));
        assertTrue(visitors.contains(visitor2));
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
