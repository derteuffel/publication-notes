package com.derteuffel.publicationNotes.repositories;

import com.derteuffel.publicationNotes.entities.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {

    List<Options> findAllByDepartement_Id(Long id);
    Options findByNameOrSlug(String name, String slug);
}
