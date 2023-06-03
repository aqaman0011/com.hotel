package controllers;


import models.Person;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person createdPerson = personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @GetMapping("/{passportNumber}")
    public ResponseEntity<Person> getPersonByPassportNumber(@PathVariable String passportNumber) throws ChangeSetPersister.NotFoundException {
        Person person = personService.getPersonByPassportNumber(passportNumber);
        return ResponseEntity.ok(person);
    }

    @PutMapping("/{passportNumber}")
    public ResponseEntity<Person> updatePerson(@PathVariable String passportNumber, @RequestBody Person updatedPerson) throws ChangeSetPersister.NotFoundException {
        Person person = personService.getPersonByPassportNumber(passportNumber);
        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
        personService.updatePerson(person);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{passportNumber}")
    public ResponseEntity<Void> deletePerson(@PathVariable String passportNumber) {
        try {
            Person person = personService.getPersonByPassportNumber(passportNumber);
            personService.deletePerson(person);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
