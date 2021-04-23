package com.derteuffel.publicationNotes.controllers;

import com.derteuffel.publicationNotes.entities.Post;
import com.derteuffel.publicationNotes.entities.User;
import com.derteuffel.publicationNotes.entities.UserInfo;
import com.derteuffel.publicationNotes.helpers.PostDto;
import com.derteuffel.publicationNotes.repositories.UserRepository;
import com.derteuffel.publicationNotes.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostController {


    @Autowired
    private PostService postService;
    @Value("${file.upload-dir}")
    private String location ;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/options/{id}")
    public ResponseEntity<List<Post>> findAllByOptions_Id(@PathVariable Long id) {
        List<Post> lists = new ArrayList<>();
        try{
            postService.findAllByOptions_Id(id).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Post>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<List<Post>> findAllByStudent(@PathVariable Long id) {
        User user = userRepository.getOne(id);
        UserInfo userInfo = user.getUserInfo();
        List<Post> lists = new ArrayList<>();
        try{
            postService.findAllByOptions_Id(userInfo.getOptions().getId()).forEach(lists :: add);
            postService.findAllByDepartement_Id(userInfo.getOptions().getDepartement().getId()).forEach(lists :: add);
            postService.findAllByFaculty_Id(userInfo.getOptions().getDepartement().getFaculty().getId()).forEach(lists::add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Post>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/departement/{id}")
    public ResponseEntity<List<Post>> findAllByDepartement_Id(@PathVariable Long id) {
        List<Post> lists = new ArrayList<>();
        try{
            postService.findAllByDepartement_Id(id).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Post>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<List<Post>> findAllByFaculty_Id(@PathVariable Long id) {
        List<Post> lists = new ArrayList<>();
        try{
            postService.findAllByFaculty_Id(id).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Post>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Post>> findAllByUser_Id(@PathVariable Long id) {
        List<Post> lists = new ArrayList<>();
        try{
            postService.findAllByUser_Id(id).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Post>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        List<Post> lists = new ArrayList<>();
        try{
            postService.findAll().forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Post>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getOne(@PathVariable Long id) {

        Post post = postService.getOne(id);
        try {
            if (post != null){
                return new ResponseEntity<>(post, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Post) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("title")String title, @RequestParam("description") String description, @RequestParam("facultyId")String facultyId, @RequestParam("departementId")String departementId, @RequestParam("optionsId") String optionsId, @RequestParam("fichier") MultipartFile file, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findByUsername(principal.getName()).get();
        System.out.println(user.getUsername());
        PostDto postDto = new PostDto();
        try {
            if (facultyId != null){
                System.out.println("je suis dans faculty");
              postDto.setFacultyId(Long.parseLong(facultyId));
            }
            if (departementId != null){
                System.out.println("je suis dans departement");
                postDto.setDepartementId(Long.parseLong(departementId));
                System.out.println("j'ai fini dans departement");
            }
            if (optionsId != null){
                System.out.println("je suis dans options");
                postDto.setOptionId(Long.parseLong(optionsId));
            }

            System.out.println("je suis ici");
            postDto.setTitle(title);
            postDto.setDescription(description);

            try {

                if (!(file.isEmpty())) {

                    // Get the file and save it somewhere
                    Path path = Paths.get(location +"/"+ file.getOriginalFilename());
                    byte[] bytes = file.getBytes();

                    Files.write(path, bytes);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            postDto.setFile(location+"/"+file.getOriginalFilename());

            postService.save(postDto, user.getId());

            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
