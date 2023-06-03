package services;

import models.Person;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import repositories.PersonRepository;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonById(Long personId) throws ChangeSetPersister.NotFoundException {
        return personRepository.findById(personId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Person getPersonByPassportNumber(String passportNumber) throws ChangeSetPersister.NotFoundException {
        Optional<Person> personOptional = Optional.ofNullable(personRepository.findByPassportNumber(passportNumber));
        return personOptional.orElseThrow(ChangeSetPersister.NotFoundException::new);
    }


    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public void updatePerson(Person person) {
        personRepository.save(person);
    }

    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    public Person createPerson(String firstName, String lastName, int age) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);

        return personRepository.save(person);
    }
}
