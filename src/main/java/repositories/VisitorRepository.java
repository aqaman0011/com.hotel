package repositories;


import models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    List<Visitor> findByPersonLastName(String lastName);
    Visitor findByPersonPassportNumber(String passportNumber);
}
