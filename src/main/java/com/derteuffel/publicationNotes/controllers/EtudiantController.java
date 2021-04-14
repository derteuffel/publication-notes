package com.derteuffel.publicationNotes.controllers;

import com.derteuffel.publicationNotes.entities.User;
import com.derteuffel.publicationNotes.entities.UserInfo;
import com.derteuffel.publicationNotes.enums.Role;
import com.derteuffel.publicationNotes.helpers.UserInfoDto;
import com.derteuffel.publicationNotes.repositories.UserRepository;
import com.derteuffel.publicationNotes.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String location ;



    @PostMapping("/{id}")
    public ResponseEntity<UserInfo> save(@Valid @RequestBody UserInfoDto userInfo, @PathVariable Long id) {

        UserInfo user = userInfoService.save(userInfo, id);
         return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getOne(@PathVariable Long id) {
        try{
            UserInfo userInfo = userInfoService.getOne(id);
            if (userInfo != null){
                return new ResponseEntity<>(userInfo, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((UserInfo) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserInfo>> findAll() {


        List<UserInfo> lists = new ArrayList<>();
        List<User> userList = userRepository.findAll();

        try {
            for (User user : userList){
                if (user.getRole().equals(Role.ETUDIANT)){
                    lists.add(user.getUserInfo());
                }
            }
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<UserInfo>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/options/{id}")
    public ResponseEntity<List<UserInfo>> findAllByOptions(@PathVariable Long id) {

        System.out.println("je suis ici");


        List<UserInfo> lists = new ArrayList<>();
        List<UserInfo> userInfos = userInfoService.getAllByOptions(id);
        System.out.println(userInfos);

        try {
           userInfos.forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<UserInfo>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try{
            userInfoService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserInfo> update(@Valid @RequestBody UserInfo userInfo, @PathVariable Long id) {
        try{
            userInfoService.update(userInfo, id);
            return new ResponseEntity<>(userInfo, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>((UserInfo) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/many/saves/{id}")
    public void saveUsers(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
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
        userInfoService.saveUsers(location+"/"+file.getOriginalFilename(), id);
    }
}
