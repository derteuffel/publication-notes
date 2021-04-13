package com.derteuffel.publicationNotes.controllers;

import com.derteuffel.publicationNotes.entities.Note;
import com.derteuffel.publicationNotes.entities.Session;
import com.derteuffel.publicationNotes.helpers.NoteDto;
import com.derteuffel.publicationNotes.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private SessionService sessionService;

    @Value("${file.upload-dir}")
    private String location ;

    @PostMapping("")
    public ResponseEntity<?> addNote(@RequestParam("fichier") MultipartFile file,
                                     @RequestParam("periode") String periode,
                                     @RequestParam("annee") String annee,
                                     @RequestParam("filiere") String filiere,
                                     @RequestParam("niveau") String niveau) throws IOException {

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

        NoteDto noteDto =  new NoteDto();
        noteDto.setAnnee(annee);
        noteDto.setFiliere(filiere);
        noteDto.setNiveau(niveau);
        noteDto.setPeriode(periode);
        sessionService.addNote(location+"/"+file.getOriginalFilename(), noteDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<List<Session>> getAllByUser(@PathVariable Long id){
        List<Session> lists = new ArrayList<>();
        try{
            sessionService.getSessionsByUser(id).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Session>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/niveau/{id}")
    public ResponseEntity<List<Session>> getAllByUserAndNiveau(@PathVariable Long id, @RequestParam("niveau") String niveau){
        System.out.println(niveau);
        List<Session> lists = new ArrayList<>();
        try{
            sessionService.findAllByUserAndNiveau(id, niveau).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Session>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/users/matricule/periode/niveau")
    public ResponseEntity<Session> getSessionByUserAndMatriculeAndPeriodeAndNiveau(@RequestParam("matricule") String matricule,
                                                                                   @RequestParam("name") String name,
                                                                                   @RequestParam("niveau") String niveau){
        System.out.println(matricule+" -- "+name+" -- "+niveau);
        Session session = sessionService.findByUserMatriculeAndNameAndNiveau(matricule,name,niveau);
        try{
            if (session != null){
                return new ResponseEntity<>(session, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Session) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/periode/niveau/{id}")
    public ResponseEntity<Session> getSessionByUserAndIdAndPeriodeAndNiveau(@PathVariable Long id,
                                                                                   @RequestParam("name") String name,
                                                                                   @RequestParam("niveau") String niveau){
        Session session = sessionService.findByUserIdAndNameAndNiveau(id,name,niveau);
        try{
            if (session != null){
                return new ResponseEntity<>(session, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Session) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Session> getOne(@PathVariable Long id){
        Session session = sessionService.getOne(id);
        try{
            if (session != null){
                return new ResponseEntity<>(session, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Session) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/sessions/{id}")
    public ResponseEntity<List<Note>> getNotesBySession(@PathVariable Long id){
        List<Note> lists = new ArrayList<>();

        try{
            sessionService.getNoteBySession(id).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Note>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

