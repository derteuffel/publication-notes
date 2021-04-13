package com.derteuffel.publicationNotes.repositories;

import com.derteuffel.publicationNotes.entities.Session;
import com.derteuffel.publicationNotes.enums.Niveau;
import com.derteuffel.publicationNotes.enums.Periode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findByUserInfo_MatriculeAndNameAndNiveau(String matricule, Periode name, Niveau niveau);
    Session findByUserInfo_IdAndNameAndNiveau(Long id, Periode name, Niveau niveau);
    List<Session> findAllByUserInfo_IdAndNiveau(Long id, Niveau niveau);
    List<Session> findAllByUserInfo_Id(Long id);
    List<Session> findAllByNameAndNiveauAndSessionDate(Periode name, Niveau niveau, String sessionDate);
}
