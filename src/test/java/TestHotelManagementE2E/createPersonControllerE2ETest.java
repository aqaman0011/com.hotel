package TestHotelManagementE2E;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import repositories.PersonRepository;
import models.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class createPersonControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testAddPerson() {
        // Arrange
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setPassportNumber("AB123456");

        HttpEntity<Person> request = new HttpEntity<>(person);

        // Act
        ResponseEntity<Person> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/persons", request, Person.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Person savedPerson = response.getBody();
        assertEquals("John", savedPerson.getFirstName());
        assertEquals("Doe", savedPerson.getLastName());
        assertEquals("AB123456", savedPerson.getPassportNumber());

        // Clean up
        personRepository.deleteById(savedPerson.getId());
    }

}
