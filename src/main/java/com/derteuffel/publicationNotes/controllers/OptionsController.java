package com.derteuffel.publicationNotes.controllers;

import com.derteuffel.publicationNotes.entities.Options;
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

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/options")
public class OptionsController {

    @Autowired
    private OptionsService optionsService;

    @Value("${file.upload-dir}")
    private String location ;

    @PostMapping("/{id}")
    public ResponseEntity<?> saveOptions(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
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

    @GetMapping("/{id}")
    public ResponseEntity<Options> getOneOptions(@PathVariable Long id){
        Options options = optionsService.getOne(id);
        try{
            if (options != null){
                return new ResponseEntity<>(options, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((Options) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
