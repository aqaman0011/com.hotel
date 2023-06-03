package services;


import models.Visitor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import repositories.VisitorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    public Visitor getVisitorById(Long visitorId) throws ChangeSetPersister.NotFoundException {
        return visitorRepository.findById(visitorId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<Visitor> getVisitorsByLastName(String lastName) {
        return visitorRepository.findByPersonLastName(lastName);
    }

    public Visitor getVisitorByPassportNumber(String passportNumber) throws ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(visitorRepository.findByPersonPassportNumber(passportNumber))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Visitor addVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    public void updateVisitor(Visitor visitor) {
        visitorRepository.save(visitor);
    }

    public void deleteVisitor(Long visitorId) throws ChangeSetPersister.NotFoundException {
        Visitor visitor = getVisitorById(visitorId);
        visitorRepository.delete(visitor);
    }

}
