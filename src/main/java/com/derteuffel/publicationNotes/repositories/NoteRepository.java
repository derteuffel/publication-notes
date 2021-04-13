package com.derteuffel.publicationNotes.repositories;

import com.derteuffel.publicationNotes.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllBySession_Id(Long id);
}
