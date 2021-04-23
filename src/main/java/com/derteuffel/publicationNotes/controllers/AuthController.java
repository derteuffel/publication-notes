package com.derteuffel.publicationNotes.controllers;


import com.derteuffel.publicationNotes.entities.User;
import com.derteuffel.publicationNotes.enums.Role;
import com.derteuffel.publicationNotes.helpers.UserInfoDto;
import com.derteuffel.publicationNotes.messages.requests.LoginRequest;
import com.derteuffel.publicationNotes.messages.requests.SignupRequest;
import com.derteuffel.publicationNotes.messages.responses.MessageResponse;
import com.derteuffel.publicationNotes.repositories.UserRepository;
import com.derteuffel.publicationNotes.security.jwt.JwtTokenProvider;
import com.derteuffel.publicationNotes.services.UserDetailsServiceImpl;
import com.derteuffel.publicationNotes.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenProvider jwtUtils;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/login")
    public ResponseEntity<?> login(Principal principal){
        System.out.println("je suis dans la fonction");
        if(principal == null){
            //This should be ok http status because this will be used for logout path.
            return ResponseEntity.ok(principal);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        System.out.println(authenticationToken.getName());
        User user = userRepository.findByUsername(authenticationToken.getName()).get();
        user.setToken(jwtUtils.generateToken(authenticationToken));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login/mobile")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

        System.out.println(authenticationRequest.getUsername()+"  "+authenticationRequest.getPassword());
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());



        final String token = jwtUtils.generateToken(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        System.out.println(token);

        User user = userRepository.findByUsername(authenticationRequest.getUsername()).get();
        user.setToken(token);
        userRepository.save(user);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> enregistrerCompte(@Valid @RequestBody SignupRequest requete) {
        System.out.println(requete.getRole());
        if (userRepository.existsByUsername(requete.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (userRepository.existsByEmail(requete.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Creer un nouveau compte utilisateur
        User user = new User();
        user.setUsername(requete.getUsername());
        user.setEmail(requete.getEmail());
        user.setActive(false);
        user.setPassword(encoder.encode(requete.getPassword()));

                switch (requete.getRole()) {
                    case "ADMIN":
                        user.setRole(Role.ADMIN);
                        break;
                    case "ENCODEUR":
                        user.setRole(Role.ENCODEUR);
                        break;
                    case "ETUDIAN":
                        user.setRole(Role.ETUDIANT);
                        break;
                    case "DOYEN":
                        user.setRole(Role.DOYEN);
                        break;
                    case "NOTE":
                        user.setRole(Role.NOTE);
                        break;
                    case "PAIEMENT":
                        user.setRole(Role.PAIEMENT);
                        break;
                    default:
                        user.setRole(Role.ROOT);
                        break;
                }
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistre avec success!","succes"));
    }

    @PostMapping("/new/account")
    public ResponseEntity<?> enregistrerUtilisateur(@Valid @RequestBody UserInfoDto requete) {
        System.out.println(requete.getRole());
        if (userRepository.existsByUsername(requete.getMatricule())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur: Nom utilisateur deja utilise!"));
        }

        if (userRepository.existsByEmail(requete.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur: Email deja utilise!"));
        }
        userInfoService.saveForAccount(requete);

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistre avec success!","succes"));
    }
}
