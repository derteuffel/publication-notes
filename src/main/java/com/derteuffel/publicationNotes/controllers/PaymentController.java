package com.derteuffel.publicationNotes.controllers;

import com.derteuffel.publicationNotes.entities.Payment;
import com.derteuffel.publicationNotes.helpers.ActivationDto;
import com.derteuffel.publicationNotes.helpers.PaymentDto;
import com.derteuffel.publicationNotes.messages.responses.MessageResponse;
import com.derteuffel.publicationNotes.services.PaymentService;
import com.derteuffel.publicationNotes.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> addPayment(@Valid @RequestBody PaymentDto paymentDto){
        System.out.println(paymentDto.getDescription());
        try{
            paymentService.makePayment(paymentDto);
            return  ResponseEntity.ok(new MessageResponse("Votre paiement a ete effectuer avec succes","succes"));
        }catch (Exception e){
            return ResponseEntity.ok(new MessageResponse("Votre Paiement n'a pas reussi","error"));
        }
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> validPayment(@Valid @RequestBody ActivationDto activationDto, @PathVariable Long id){
        try{
            paymentService.activatePayment(id,activationDto);
            return  ResponseEntity.ok(new MessageResponse("Vous avez confirmer paiement a ete effectuer avec succes","succes"));
        }catch (Exception e){
            return ResponseEntity.ok(new MessageResponse("Votre confirmation de Paiement n'a pas reussi","error"));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Payment>> findAllByUser(@PathVariable Long id){
        List<Payment> lists = new ArrayList<>();
        try{
            paymentService.getAllByEtudiant(id).forEach(lists :: add);
            if (lists.isEmpty()){
                System.out.println(lists);
                return new ResponseEntity<>(lists,HttpStatus.NO_CONTENT);
            }else {
                System.out.println(lists);
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>((List<Payment>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sent/{id}")
    public ResponseEntity<?> ressentCode(@Valid @RequestBody ActivationDto activationDto, @PathVariable Long id){
        try{
            paymentService.resentCode(id,activationDto);
            return  ResponseEntity.ok(new MessageResponse("Le code que de paiement a ete renvoyer avec succes","succes"));
        }catch (Exception e){
            return ResponseEntity.ok(new MessageResponse("Erreur lors de l'envoi de votre code","error"));
        }
    }
}
