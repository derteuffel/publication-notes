package com.derteuffel.publicationNotes.repositories;

import com.derteuffel.publicationNotes.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Faculty findByNameOrSlug(String name, String slug);

    Faculty findByUser_Id(Long id);
}
