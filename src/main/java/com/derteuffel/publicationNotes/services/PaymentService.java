package com.derteuffel.publicationNotes.services;

import com.derteuffel.publicationNotes.entities.Payment;
import com.derteuffel.publicationNotes.entities.UserInfo;
import com.derteuffel.publicationNotes.helpers.ActivationDto;
import com.derteuffel.publicationNotes.helpers.Generations;
import com.derteuffel.publicationNotes.helpers.MessageSending;
import com.derteuffel.publicationNotes.helpers.PaymentDto;
import com.derteuffel.publicationNotes.repositories.PaymentRepository;
import com.derteuffel.publicationNotes.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public List<Payment> getAllByEtudiant(Long id){
        List<Payment> lists = new ArrayList<>();
        paymentRepository.findAllByUserInfo_Id(id).forEach(lists :: add);

        return lists;
    }

    public void makePayment(PaymentDto paymentDto){
        System.out.println("je suis ici 1");
        Generations generations = new Generations();
        Payment payment = new Payment();
        MessageSending messageSending = new MessageSending();
        payment.setDescription(paymentDto.getDescription());
        System.out.println("je suis ici deja 3");
        payment.setTauxDuJour(paymentDto.getTaux());
        payment.setCode(generations.generateInt()+"");
        payment.setPeriode(paymentDto.getPeriode());
        if (paymentDto.getDevise().equals("USD")){
            payment.setMontantDollar(Double.parseDouble(paymentDto.getMontant()));
            payment.setMontantFranc(Double.parseDouble(paymentDto.getMontant())*paymentDto.getTaux());
        }else {
            payment.setMontantFranc(Double.parseDouble(paymentDto.getMontant()));
            payment.setMontantDollar(Double.parseDouble(paymentDto.getMontant())/paymentDto.getTaux());
        }

        System.out.println("je suis ici 2");
        UserInfo userInfo = userInfoRepository.findByMatricule(paymentDto.getMatricule()).get();
        System.out.println("je suis la 3 ");

        switch (paymentDto.getPeriode()){
            case "MINERVALLES_ACOMPTE":
                //userInfo.setAccount(true);
                System.out.println("je suis dans acompte");
                messageSending.sendMessage(""+paymentDto.getTelephone(),"Votre acompte a ete payer avec succes, vous allez recevoir votre code d'ativation");
                messageSending.sendMessage(""+paymentDto.getTelephone()," Votre code d'ativation :"+ payment.getCode());
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre acompte a ete payer avec succes, vous allez recevoir votre code d'ativation");
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre code d'ativation :"+ payment.getCode());
                break;
            case "MINERVALLES_TOTALITE":
                //userInfo.setMinervalles(true);
                System.out.println("je suis dans totalite min");
                messageSending.sendMessage(""+paymentDto.getTelephone(),"Vous avez payer avec succes la totalite de votre minervalle, vous allez recevoir votre code d'ativation");
                messageSending.sendMessage(""+paymentDto.getTelephone()," Votre code d'ativation :"+ payment.getCode());
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Vous avez payer avec succes la totalite de votre minervalle, vous allez recevoir votre code d'ativation");
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre code d'ativation :"+ payment.getCode());
                break;
            case "ENROLLEMENT_MI_SESSION":
                //userInfo.setEnrollementMiSession(true);
                System.out.println("je suis dans mi session");
                messageSending.sendMessage(""+paymentDto.getTelephone(),"Votre enrollement a ete payer avec succes, vous allez recevoir votre code d'ativation");
                messageSending.sendMessage(""+paymentDto.getTelephone()," Votre code d'ativation :"+ payment.getCode());
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre enrollement a ete payer avec succes, vous allez recevoir votre code d'ativation");
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre code d'ativation :"+ payment.getCode());
                break;
            case "ENROLLEMENT_PREMIERE_SESSION":
                //userInfo.setEnrollementPremiereSession(true);
                System.out.println("je suis dans premiere session");
                messageSending.sendMessage(""+paymentDto.getTelephone(),"Votre enrollement a ete payer avec succes, vous allez recevoir votre code d'ativation");
                messageSending.sendMessage(""+paymentDto.getTelephone()," Votre code d'ativation :"+ payment.getCode());
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre enrollement a ete payer avec succes, vous allez recevoir votre code d'ativation");
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre code d'ativation :"+ payment.getCode());
                break;
            case "ENROLLEMENT_DEUXIEME_SESSION":
                //userInfo.setEnrollementDeuxiemeSession(true);
                System.out.println("je suis dans deuxieme session");
                messageSending.sendMessage(""+paymentDto.getTelephone(),"Votre enrollement a ete payer avec succes, vous allez recevoir votre code d'ativation");
                messageSending.sendMessage(""+paymentDto.getTelephone()," Votre code d'ativation :"+ payment.getCode());
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre enrollement a ete payer avec succes, vous allez recevoir votre code d'ativation");
                System.out.println("sent message to :"+ paymentDto.getTelephone()+" Votre code d'ativation :"+ payment.getCode());
                break;
                default:
                    System.out.println(" Aucune action possible");
                    System.out.println(" Aucune code n'est disponible");
                    break;
        }
        payment.setUserInfo(userInfo);
        System.out.println("je suis a la fin");
        List<Payment> tmps = paymentRepository.findAllByUserInfo_Id(userInfo.getId());
        System.out.println("je contient : "+tmps.size());
        payment.setNumPaiement("N0_"+(paymentRepository.findAllByUserInfo_Id(userInfo.getId()).size()+1));
        paymentRepository.save(payment);
    }


    public void activatePayment(Long id, ActivationDto activationDto){

        System.out.println("je suis dans la foncfion");
        UserInfo userInfo = userInfoRepository.getOne(id);
        MessageSending messageSending = new MessageSending();
        Payment payment = paymentRepository.findByUserInfo_IdAndPeriode(userInfo.getId(), activationDto.getPeriode());
        System.out.println("pret a entrer dans la boucle");
        if (payment != null) {

            System.out.println("test paiement ------- ");
            if (payment.getPeriode().equals(activationDto.getPeriode())) {
                System.out.println("test periode matches");
                switch (activationDto.getPeriode()) {
                    case "MINERVALLES_ACOMPTE":
                        if (userInfo.getAccount() == false) {
                            System.out.println("test minervalle acompte");
                            userInfo.setAccount(true);
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Vous avez activer avec succes votre paiement de votre acompte");
                            System.out.println("sent message to :" + activationDto.getTelephone() + " Votre acompte a ete payer avec succes, et votre activation a ete fait avec succes");
                        }else {
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                            System.out.println("sent message to :" + activationDto.getTelephone() + " ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                        }
                        break;
                    case "MINERVALLES_TOTALITE":
                        if (userInfo.getMinervalles() == false) {
                            System.out.println("test minervalle totalite");
                            userInfo.setMinervalles(true);
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Felicitation!!! vous avez activer votre paiement pour la totalite de vos minervalles");
                            System.out.println("sent message to :" + activationDto.getTelephone() + " Vous avez payer avec succes la totalite de votre minervalle, et votre activation a ete fait avec succes");
                        }else {
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                            System.out.println("sent message to :" + activationDto.getTelephone() + "Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                        }
                        break;
                    case "ENROLLEMENT_MI_SESSION":
                        if (userInfo.getEnrollementMiSession() == false) {
                            System.out.println("test mi session");
                            userInfo.setEnrollementMiSession(true);
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Votre enrollement a ete payer avec succes, et votre activation a ete fait avec succes");
                            System.out.println("sent message to :" + activationDto.getTelephone() + " Votre enrollement a ete payer avec succes, et votre activation a ete fait avec succes");
                        }else {
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                            System.out.println("sent message to :" + activationDto.getTelephone() + "Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                        }
                        break;
                    case "ENROLLEMENT_PREMIERE_SESSION":
                        if (userInfo.getEnrollementPremiereSession() == false) {
                            System.out.println("test pre-session");
                            userInfo.setEnrollementPremiereSession(true);
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Votre enrollement a ete payer avec succes, et votre activation a ete fait avec succes");
                            System.out.println("sent message to :" + activationDto.getTelephone() + " Votre enrollement a ete payer avec succes, et votre activation a ete fait avec succes");
                        }else {
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                            System.out.println("sent message to :" + activationDto.getTelephone() + "Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                        }
                        break;
                    case "ENROLLEMENT_DEUXIEME_SESSION":
                        if (userInfo.getEnrollementDeuxiemeSession() == false) {
                            System.out.println("test deux-session");
                            userInfo.setEnrollementDeuxiemeSession(true);
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Votre enrollement a ete payer avec succes, et votre activation a ete fait avec succes");
                            System.out.println("sent message to :" + activationDto.getTelephone() + " Votre enrollement a ete payer avec succes, et votre activation a ete fait avec succes");
                        }else {
                            messageSending.sendMessage(""+activationDto.getTelephone(),"Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                            System.out.println("sent message to :" + activationDto.getTelephone() + "Desole! ce code a deja ete utiliser ou est incorect, Veillez vous rendre au point de peirment ou vous avez effectuer votre paiement pour plus d'information");
                        }
                        break;
                    default:
                        System.out.println(" Aucune action possible");
                        System.out.println(" Aucune code n'est disponible");
                }

            }else {
                System.out.println("periode not matches");
            }

        }else {
            System.out.println("payment did not exist");
        }
        userInfoRepository.save(userInfo);
    }


    public void resentCode(Long id, ActivationDto activationDto){
        UserInfo userInfo = userInfoRepository.getOne(id);
        MessageSending messageSending = new MessageSending();
        Payment payment = paymentRepository.findByUserInfo_IdAndPeriode(userInfo.getId(), activationDto.getPeriode());

        if (payment != null){
            messageSending.sendMessage(""+activationDto.getTelephone(),"Votre code d'activation : "+ payment.getCode());
            System.out.println("Code has been sent successfuly to "+ activationDto.getTelephone()+" Code value: "+payment.getCode());
        }else {
            messageSending.sendMessage(""+activationDto.getTelephone(),"Aucun paiement n'existe pour ce compte, Veuillez-vous diriger vers votre point de paiement ");
            System.out.println("This payment did not exist, go to your payment box please");
        }
    }
}
