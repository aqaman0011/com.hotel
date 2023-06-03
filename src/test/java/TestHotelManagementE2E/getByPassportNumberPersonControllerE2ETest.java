package TestHotelManagementE2E;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import repositories.PersonRepository;
import models.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class getByPassportNumberPersonControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testGetPersonByPassportNumber() {
        // Arrange
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setPassportNumber("AB123456");
        personRepository.save(person);

        // Act
        ResponseEntity<Person> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/persons/AB123456", Person.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Person retrievedPerson = response.getBody();
        assertEquals("John", retrievedPerson.getFirstName());
        assertEquals("Doe", retrievedPerson.getLastName());
        assertEquals("AB123456", retrievedPerson.getPassportNumber());

        // Clean up
        personRepository.deleteById(retrievedPerson.getId());
    }
}
