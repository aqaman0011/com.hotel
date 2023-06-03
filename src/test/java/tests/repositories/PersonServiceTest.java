package tests.repositories;

import models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister;
import repositories.PersonRepository;
import services.PersonService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Test
    public void testGetPersonByIdExistingPerson() throws ChangeSetPersister.NotFoundException {
        // Arrange
        Long personId = 1L;
        Person expectedPerson = new Person();
        expectedPerson.setId(personId);

        PersonRepository personRepositoryMock = mock(PersonRepository.class);
        when(personRepositoryMock.findById(personId)).thenReturn(Optional.of(expectedPerson));

        PersonService personService = new PersonService(personRepositoryMock);

        // Act
        Person actualPerson = personService.getPersonById(personId);

        // Assert
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void testGetPersonByIdNonExistingPerson() {
        // Arrange
        Long personId = 1L;

        PersonRepository personRepositoryMock = mock(PersonRepository.class);
        when(personRepositoryMock.findById(personId)).thenReturn(Optional.empty());

        PersonService personService = new PersonService(personRepositoryMock);

        // Act & Assert
        assertThrows(ChangeSetPersister.NotFoundException.class, () -> personService.getPersonById(personId));
    }

}
