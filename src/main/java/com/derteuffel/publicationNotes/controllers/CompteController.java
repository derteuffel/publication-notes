package com.derteuffel.publicationNotes.controllers;

import com.derteuffel.publicationNotes.entities.User;
import com.derteuffel.publicationNotes.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class CompteController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR') or hasRole('ROOT')")
    public ResponseEntity<List<User>> findAll(){
        List<User> lists = new ArrayList<>();
        try{
            userRepository.findAll(Sort.by(Sort.Direction.ASC, "username")).forEach(lists :: add);
            if (lists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<User>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active/{id}")
    public ResponseEntity<HttpStatus> activate(@PathVariable Long id){
        User user = userRepository.getOne(id);
        if (user.getActive() == true){
            user.setActive(false);
        }else {
            user.setActive(true);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable Long id){
        try{
            User user = userRepository.getOne(id);
            if (user != null){
                return new ResponseEntity<>(user, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>((User) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
