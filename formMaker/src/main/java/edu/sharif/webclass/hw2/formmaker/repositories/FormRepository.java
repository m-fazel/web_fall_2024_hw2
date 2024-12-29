package edu.sharif.webclass.hw2.formmaker.repositories;

import edu.sharif.webclass.hw2.formmaker.models.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findByStatus(Boolean status);
}