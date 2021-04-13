package com.derteuffel.publicationNotes.controllers;

import com.derteuffel.publicationNotes.entities.Faculty;
import com.derteuffel.publicationNotes.entities.User;
import com.derteuffel.publicationNotes.repositories.UserRepository;
import com.derteuffel.publicationNotes.services.FacultyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String location ;



    @PutMapping("/{id}")
    public ResponseEntity<?> updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty){
        System.out.println(faculty.getAdressePhysique());
        Faculty existedFaculty = facultyService.getOne(id);

        try {
            if (existedFaculty != null){
                facultyService.update(existedFaculty.getId(), faculty);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<?> addFaculties(@RequestParam("file") MultipartFile file) throws IOException {
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

        facultyService.save(location +"/"+ file.getOriginalFilename());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getAll(){
        List<Faculty> lists = new ArrayList<>();
        try{
            facultyService.findAll().forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Faculty>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getOne(@PathVariable Long id){
        Faculty faculty = facultyService.getOne(id);

        try{
            if (faculty != null){
                return new ResponseEntity<>(faculty, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Faculty) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Faculty> getOneByUser(@PathVariable Long id){
        Faculty faculty = facultyService.findByUser_Id(id);

        try{
            if (faculty != null){
                return new ResponseEntity<>(faculty, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Faculty) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Faculty> getOneByUserConnected(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findByUsername(principal.getName()).get();
        Faculty faculty = facultyService.findByUser_Id(user.getId());

        try{
            if (faculty != null){
                return new ResponseEntity<>(faculty, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Faculty) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
