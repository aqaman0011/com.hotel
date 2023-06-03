package TestHotelManagementE2E;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import repositories.PersonRepository;
import models.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class updatePersonControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testUpdatePerson() {
        // Arrange
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setPassportNumber("AB123456");
        personRepository.save(person);

        Person updatedPerson = new Person();
        updatedPerson.setId(person.getId());
        updatedPerson.setFirstName("Jane");
        updatedPerson.setLastName("Smith");
        updatedPerson.setPassportNumber("CD654321");

        HttpEntity<Person> request = new HttpEntity<>(updatedPerson);

        // Act
        ResponseEntity<Person> response = restTemplate.exchange(
                "http://localhost:" + port + "/persons/" + person.getId(),
                HttpMethod.PUT, request, Person.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Person savedPerson = response.getBody();
        assertNotNull(savedPerson);
        assertEquals("Jane", savedPerson.getFirstName());
        assertEquals("Smith", savedPerson.getLastName());
        assertEquals("CD654321", savedPerson.getPassportNumber());

        // Clean up
        personRepository.deleteById(savedPerson.getId());
    }

}
