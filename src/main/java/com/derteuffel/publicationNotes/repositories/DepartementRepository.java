package com.derteuffel.publicationNotes.repositories;

import com.derteuffel.publicationNotes.entities.Departement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    List<Departement> findAllByFaculty_Id(Long id, Sort sort);

    Departement findByNameOrSlug(String name, String slug);
}
