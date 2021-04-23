package com.derteuffel.publicationNotes.repositories;

import com.derteuffel.publicationNotes.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findAllByOptions_Id(Long id);
    List<Post> findAllByDepartement_Id(Long id);
    List<Post> findAllByFaculty_Id(Long id);
    List<Post> findAllByUser_Id(Long id);


}
