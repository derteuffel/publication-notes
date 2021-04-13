package com.derteuffel.publicationNotes.repositories;

import com.derteuffel.publicationNotes.entities.UserInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByMatricule(String matricule);
    List<UserInfo> findAllByOptions_Id(Long id, Sort sort);
}
