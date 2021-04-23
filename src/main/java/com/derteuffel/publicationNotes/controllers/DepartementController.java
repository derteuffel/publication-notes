package com.derteuffel.publicationNotes.controllers;

import com.derteuffel.publicationNotes.entities.Departement;
import com.derteuffel.publicationNotes.entities.Options;
import com.derteuffel.publicationNotes.services.DepartementService;
import com.derteuffel.publicationNotes.services.OptionsService;
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
@RequestMapping("/api/departements")
public class DepartementController {


    @Autowired
    private DepartementService departementService;

    @Autowired
    private OptionsService optionsService;

    @Value("${file.upload-dir}")
    private String location ;


    @GetMapping("/faculty/{id}")
    public ResponseEntity<List<Departement>> getAllByFaculty(@PathVariable Long id){
        List<Departement> lists = new ArrayList<>();

        try {
            departementService.findAllByFaculty_Id(id).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return  new ResponseEntity<>((List<Departement>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Departement> getOne(@PathVariable Long id){
        Departement departement = departementService.getOne(id);
        try{
            if (departement != null){
                return new ResponseEntity<>(departement, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Departement) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartement(@PathVariable Long id, @RequestBody Departement departement){
        Departement existedDepartement = departementService.getOne(id);

        try{
            if (existedDepartement != null){
                departementService.updateDepartement(departement, existedDepartement.getId());
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/options/{id}")
    public ResponseEntity<List<Options>> getAlloptionsByDepartement(@PathVariable Long id){
        List<Options> lists = new ArrayList<>();
        try{
            optionsService.findAllByDepartement_Id(id).forEach(lists :: add);
            if (lists.isEmpty()){
                System.out.println(lists);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                System.out.println(lists);
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Options>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Departement>> getAll(){
        List<Departement> lists = new ArrayList<>();
        try{
            departementService.findAll().forEach(lists :: add);
            if (lists.isEmpty()){
                System.out.println(lists);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                System.out.println(lists);
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Departement>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/options/save/{id}")
    public ResponseEntity<?> addOptions(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
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

        optionsService.saveOption(location +"/"+ file.getOriginalFilename(), id);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/save/{id}")
    public ResponseEntity<?> addDepartement(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
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

        departementService.save(location +"/"+ file.getOriginalFilename(), id);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
