package com.derteuffel.publicationNotes.services;

import com.derteuffel.publicationNotes.entities.*;
import com.derteuffel.publicationNotes.helpers.PostDto;
import com.derteuffel.publicationNotes.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private FacultyRepository facultyRepository;


    public List<Post> findAllByOptions_Id(Long id) {
        return postRepository.findAllByOptions_Id(id);
    }

    public List<Post> findAllByDepartement_Id(Long id) {
        return postRepository.findAllByDepartement_Id(id);
    }

    public List<Post> findAllByFaculty_Id(Long id) {
        return postRepository.findAllByFaculty_Id(id);
    }

    public List<Post> findAllByUser_Id(Long id) {
        return postRepository.findAllByUser_Id(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post getOne(Long id) {
        return postRepository.getOne(id);
    }

    public Post save(PostDto postDto, Long id) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        User user = userRepository.getOne(id);
        Post post = new Post();
        post.setAddedDate(sdf.format(new Date()));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setFile(postDto.getFile());
        post.setUser(user);
        if (postDto.getDepartementId() != null){
            Departement departement = departementRepository.getOne(postDto.getDepartementId());
            post.setDepartement(departement);
        }
        if (postDto.getFacultyId() != null){
            Faculty faculty = facultyRepository.getOne(postDto.getFacultyId());
            post.setFaculty(faculty);
        }

        if (postDto.getOptionId() != null){
            Options options = optionsRepository.getOne(postDto.getOptionId());
            post.setOptions(options);
        }

        return postRepository.save(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
