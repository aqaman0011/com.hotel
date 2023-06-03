package controllers;

import models.Visitor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.VisitorService;

import java.util.List;

@RestController
@RequestMapping("/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        return ResponseEntity.ok(visitors);
    }

    @GetMapping("/{visitorId}")
    public ResponseEntity<Visitor> getVisitorById(@PathVariable Long visitorId) {
        try {
            Visitor visitor = visitorService.getVisitorById(visitorId);
            return ResponseEntity.ok(visitor);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<Visitor>> getVisitorsByLastName(@PathVariable String lastName) {
        List<Visitor> visitors = visitorService.getVisitorsByLastName(lastName);
        return ResponseEntity.ok(visitors);
    }

    @GetMapping("/passportNumber/{passportNumber}")
    public ResponseEntity<Visitor> getVisitorByPassportNumber(@PathVariable String passportNumber) {
        try {
            Visitor visitor = visitorService.getVisitorByPassportNumber(passportNumber);
            return ResponseEntity.ok(visitor);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Visitor> addVisitor(@RequestBody Visitor visitor) {
        Visitor savedVisitor = visitorService.addVisitor(visitor);
        return ResponseEntity.ok(savedVisitor);
    }

    @PutMapping("/{visitorId}")
    public ResponseEntity<Void> updateVisitor(@PathVariable Long visitorId, @RequestBody Visitor visitor) {
        try {
            Visitor existingVisitor = visitorService.getVisitorById(visitorId);
            existingVisitor.setPerson(visitor.getPerson());
            existingVisitor.setRoom(visitor.getRoom());
            existingVisitor.setCheckInDate(visitor.getCheckInDate());
            existingVisitor.setCheckOutDate(visitor.getCheckOutDate());

            visitorService.updateVisitor(existingVisitor);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{visitorId}")
    public ResponseEntity<Void> deleteVisitor(@PathVariable Long visitorId) {
        try {
            visitorService.deleteVisitor(visitorId);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
