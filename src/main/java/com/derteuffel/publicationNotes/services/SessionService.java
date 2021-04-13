package com.derteuffel.publicationNotes.services;

import com.derteuffel.publicationNotes.entities.Note;
import com.derteuffel.publicationNotes.entities.Session;
import com.derteuffel.publicationNotes.entities.UserInfo;
import com.derteuffel.publicationNotes.enums.Niveau;
import com.derteuffel.publicationNotes.enums.Periode;
import com.derteuffel.publicationNotes.helpers.MessageSending;
import com.derteuffel.publicationNotes.helpers.NoteDto;
import com.derteuffel.publicationNotes.repositories.NoteRepository;
import com.derteuffel.publicationNotes.repositories.SessionRepository;
import com.derteuffel.publicationNotes.repositories.UserInfoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public void addNote(String fileName, NoteDto noteDto) throws IOException {


        FileInputStream file = new FileInputStream(new File(fileName));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, List<Integer>> data = new HashMap<>();
        Row matiereRow = sheet.getRow(6);
        Row ponderationRow = sheet.getRow(15);
        Row maxRow = sheet.getRow(16);

        List<Session> existedSessions = new ArrayList<>();
        switch (noteDto.getPeriode()) {
            case "MI_SESSION":

                switch (noteDto.getNiveau()) {
                    case "G1":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.MI_SESSION, Niveau.G1, noteDto.getAnnee());

                        break;
                    case "G2":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.MI_SESSION, Niveau.G2, noteDto.getAnnee());
                        break;
                    case "G3":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.MI_SESSION, Niveau.G3, noteDto.getAnnee());
                        break;
                    case "L1":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.MI_SESSION, Niveau.L1, noteDto.getAnnee());
                        break;
                    case "L2":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.MI_SESSION, Niveau.L2, noteDto.getAnnee());
                        break;
                    case "CYCLE_DOCTORAT":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.MI_SESSION, Niveau.CYCLE_DOCTORAT, noteDto.getAnnee());
                        break;
                }

                break;
            case "PREMIERE_SESSION":
                switch (noteDto.getNiveau()) {
                    case "G1":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.PREMIERE_SESSION, Niveau.G1, noteDto.getAnnee());
                        break;
                    case "G2":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.PREMIERE_SESSION, Niveau.G2, noteDto.getAnnee());
                        break;
                    case "G3":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.PREMIERE_SESSION, Niveau.G3, noteDto.getAnnee());
                        break;
                    case "L1":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.PREMIERE_SESSION, Niveau.L1, noteDto.getAnnee());
                        break;
                    case "L2":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.PREMIERE_SESSION, Niveau.L2, noteDto.getAnnee());
                        break;
                    case "CYCLE_DOCTORAT":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.PREMIERE_SESSION, Niveau.CYCLE_DOCTORAT, noteDto.getAnnee());
                        break;
                }
                break;
            case "DEUXIEME_SESSION":
                switch (noteDto.getNiveau()) {
                    case "G1":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.DEUXIEME_SESSION, Niveau.G1, noteDto.getAnnee());
                        break;
                    case "G2":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.DEUXIEME_SESSION, Niveau.G2, noteDto.getAnnee());
                        break;
                    case "G3":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.DEUXIEME_SESSION, Niveau.G3, noteDto.getAnnee());
                        break;
                    case "L1":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.DEUXIEME_SESSION, Niveau.L1, noteDto.getAnnee());
                        break;
                    case "L2":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.DEUXIEME_SESSION, Niveau.L2, noteDto.getAnnee());

                        break;
                    case "CYCLE_DOCTORAT":
                        existedSessions = sessionRepository.findAllByNameAndNiveauAndSessionDate(Periode.DEUXIEME_SESSION, Niveau.CYCLE_DOCTORAT, noteDto.getAnnee());
                        break;
                }
                break;
            default:
                System.out.println("Aucune valeur a afficher");
        }

        if (!(existedSessions.isEmpty())) {
            System.out.println("Vous ne pouvez pas enregistrer les notes d'une meme classe et d'une meme session a plusieurs reprises");
        } else {

            int i = 17;
            System.out.println("je contient :" + sheet.getPhysicalNumberOfRows() + "Ligne");
            for (int i1 = 17; i1 <= (sheet.getPhysicalNumberOfRows() - 1); i1++) {
                UserInfo userInfo = new UserInfo();
                // get new user information from his matricule
                switch (sheet.getRow(i1).getCell(1).getCellTypeEnum()) {
                    case STRING:
                        userInfo = userInfoRepository.findByMatricule(sheet.getRow(i1).getCell(1).getStringCellValue()).get();
                        System.out.println("je suis l'utilisateur :" + userInfo.getFirstName());
                        break;
                    case FORMULA:
                        userInfo = userInfoRepository.findByMatricule(sheet.getRow(i1).getCell(1).getCellFormula()).get();
                        System.out.println("je suis l'utilisateur :" + userInfo.getFirstName());
                        break;
                    default:
                        System.out.println("nothing to do");
                }

                // new session
                Session session = new Session();
                MessageSending messageSending = new MessageSending();
                session.setSessionDate(noteDto.getAnnee());
                session.setUserInfo(userInfo);
                session.setTotal((int) sheet.getRow(i1).getCell((sheet.getRow(i1).getPhysicalNumberOfCells() - 5)).getNumericCellValue());
                session.setPercentage(sheet.getRow(i1).getCell((sheet.getRow(i1).getPhysicalNumberOfCells() - 4)).getNumericCellValue());
                session.setUvLostNumber((int) sheet.getRow(i1).getCell((sheet.getRow(i1).getPhysicalNumberOfCells() - 3)).getNumericCellValue());
                switch (noteDto.getPeriode()) {
                    case "MI_SESSION":

                        switch (noteDto.getNiveau()) {
                            case "G1":
                                    if (session.getNiveau().equals(userInfo.getNiveau())){
                                        session.setUserInfo(userInfo);
                                        session.setName(Periode.MI_SESSION);
                                        session.setNiveau(Niveau.G1);
                                        sessionRepository.save(session);
                                    }else {
                                        System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                    }

                                break;
                            case "G2":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.MI_SESSION);
                                    session.setNiveau(Niveau.G2);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "G3":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.MI_SESSION);
                                    session.setNiveau(Niveau.G3);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "PRE_LICENCE":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.MI_SESSION);
                                    session.setNiveau(Niveau.PRE_LICENCE);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "L1":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.MI_SESSION);
                                    session.setNiveau(Niveau.L1);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "L2":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.MI_SESSION);
                                    session.setNiveau(Niveau.L2);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "CYCLE_DOCTORAT":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.MI_SESSION);
                                    session.setNiveau(Niveau.CYCLE_DOCTORAT);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                        }
                        messageSending.sendMessage(userInfo.getTelephone(),"Bonjour "+userInfo.getFirstName()+" "+userInfo.getLastName()+" "+userInfo.getName()+" Vos notes de la Mi-Session sont disponible, Veillez consulter a partir de votre application mobile");

                        break;
                    case "PREMIERE_SESSION":
                        switch (noteDto.getNiveau()) {
                            case "G1":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.PREMIERE_SESSION);
                                    session.setNiveau(Niveau.G1);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "G2":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.PREMIERE_SESSION);
                                    session.setNiveau(Niveau.G2);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "G3":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.PREMIERE_SESSION);
                                    session.setNiveau(Niveau.G3);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "PRE_LICENCE":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.PREMIERE_SESSION);
                                    session.setNiveau(Niveau.PRE_LICENCE);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "L1":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.PREMIERE_SESSION);
                                    session.setNiveau(Niveau.L1);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "L2":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.PREMIERE_SESSION);
                                    session.setNiveau(Niveau.L2);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "CYCLE_DOCTORAT":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.PREMIERE_SESSION);
                                    session.setNiveau(Niveau.CYCLE_DOCTORAT);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                        }
                        messageSending.sendMessage(userInfo.getTelephone(),"Bonjour "+userInfo.getFirstName()+" "+userInfo.getLastName()+" "+userInfo.getName()+" Vos notes de la Premiere Session sont disponible, Veillez consulter a partir de votre application mobile");
                        break;
                    case "DEUXIEME_SESSION":
                        switch (noteDto.getNiveau()) {
                            case "G1":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.DEUXIEME_SESSION);
                                    session.setNiveau(Niveau.G1);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "G2":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.DEUXIEME_SESSION);
                                    session.setNiveau(Niveau.G2);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "G3":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.DEUXIEME_SESSION);
                                    session.setNiveau(Niveau.G3);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "PRE_LICENCE":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.DEUXIEME_SESSION);
                                    session.setNiveau(Niveau.PRE_LICENCE);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "L1":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.DEUXIEME_SESSION);
                                    session.setNiveau(Niveau.L1);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "L2":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.DEUXIEME_SESSION);
                                    session.setNiveau(Niveau.L2);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }
                                break;
                            case "CYCLE_DOCTORAT":
                                if (session.getNiveau().equals(userInfo.getNiveau())){
                                    session.setUserInfo(userInfo);
                                    session.setName(Periode.DEUXIEME_SESSION);
                                    session.setNiveau(Niveau.CYCLE_DOCTORAT);
                                    sessionRepository.save(session);
                                }else {
                                    System.out.println("Cette session ne peux etre enregistrer car les niveaux de l'etudiant et de la session ne concordent pas!");
                                }

                                break;
                        }
                        messageSending.sendMessage(userInfo.getTelephone(),"Bonjour "+userInfo.getFirstName()+" "+userInfo.getLastName()+" "+userInfo.getName()+" Vos notes de la Deuxieme Session sont disponible, Veillez consulter a partir de votre application mobile");
                        break;
                    default:
                        System.out.println("Aucune valeur a afficher");
                }
                //Add new sessio array notes

                for (int j = 2; j < (sheet.getRow(i1).getPhysicalNumberOfCells() - 6); j++) {
                    Note note = new Note();
                    note.setMatiere(matiereRow.getCell(j).getStringCellValue());
                    switch (sheet.getRow(16).getCell(j).getCellTypeEnum()) {
                        case STRING:
                            note.setMax(Integer.parseInt(sheet.getRow(16).getCell(j).getStringCellValue()));
                            System.out.println("je contient :" + note.getMax());
                            break;
                        case NUMERIC:
                            note.setMax((int) sheet.getRow(16).getCell(j).getNumericCellValue());
                            System.out.println("je contient :" + note.getMax());
                            break;
                        default:
                            System.out.println("nothing to do");
                    }
                    switch (sheet.getRow(i1).getCell(j).getCellTypeEnum()) {
                        case STRING:
                            note.setNote(Integer.parseInt(sheet.getRow(i1).getCell(j).getStringCellValue()));
                            System.out.println("je contient :" + note.getNote());
                            break;
                        case NUMERIC:
                            note.setNote((int) sheet.getRow(i1).getCell(j).getNumericCellValue());
                            System.out.println("je contient :" + note.getNote());
                            break;
                        default:
                            System.out.println("nothing to do");
                    }

                    switch (ponderationRow.getCell(j).getCellTypeEnum()) {
                        case STRING:
                            note.setPonderation(Integer.parseInt(ponderationRow.getCell(j).getStringCellValue()));
                            System.out.println("je contient :" + note.getPonderation());
                            break;
                        case NUMERIC:
                            note.setPonderation((int) ponderationRow.getCell(j).getNumericCellValue());
                            System.out.println("je contient :" + note.getPonderation());
                            break;
                        default:
                            System.out.println("nothing to do");
                    }

                    note.setSession(session);
                    noteRepository.save(note);
                }
                i++;
            }
        }
    }

    public List<Session> getSessionsByUser(Long id){
        return sessionRepository.findAllByUserInfo_Id(id);
    }

    public Session findByUserMatriculeAndNameAndNiveau(String matricule, String name, String niveau){
        Session session = new Session();
        MessageSending messageSending = new MessageSending();
        UserInfo userInfo = userInfoRepository.findByMatricule(matricule).get();
        switch (name){
            case "MI_SESSION":
                switch (niveau){
                    case "G1":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.MI_SESSION, Niveau.G1);
                        break;
                    case "G2":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.MI_SESSION, Niveau.G2);
                    break;
                    case "G3":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.MI_SESSION, Niveau.G3);
                    break;
                    case "PRE_LICENCE":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.MI_SESSION, Niveau.PRE_LICENCE);
                        break;
                    case "L1":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.MI_SESSION, Niveau.L1);
                    break;
                    case "L2":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.MI_SESSION, Niveau.L2);
                    break;
                    case "CYCLE_DOCTORAT":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.MI_SESSION, Niveau.CYCLE_DOCTORAT);
                    break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                if (session != null){
                    if (((userInfo.getAccount() == false) && (userInfo.getMinervalles() == false)) && (userInfo.getEnrollementMiSession() == false)){
                        System.out.println("Vous n'avez pas finaliser avec vos minervalles et/ou votre enrollement");
                        messageSending.sendMessage(userInfo.getTelephone(),"Bonjour "+userInfo.getFirstName()+" "+userInfo.getLastName()+" "+userInfo.getName()+" Desole! vous ne pouvez pas consulter vos note a partir de ce service car vous n'avez pas finaliser avec le paiement de vos minervalles et/ou votre enrollement");
                        session = new Session();
                    }
                }
                break;
            case "PREMIERE_SESSION":
                switch (niveau){
                    case "G1":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.PREMIERE_SESSION, Niveau.G1);
                    break;
                    case "G2":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.PREMIERE_SESSION, Niveau.G2);
                    break;
                    case "G3":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.PREMIERE_SESSION, Niveau.G3);
                    break;
                    case "PRE_LICENCE":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.PREMIERE_SESSION, Niveau.PRE_LICENCE);
                        break;
                    case "L1":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.PREMIERE_SESSION, Niveau.L1);
                    break;
                    case "L2":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.PREMIERE_SESSION, Niveau.L2);
                    break;
                    case "CYCLE_DOCTORAT":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.PREMIERE_SESSION, Niveau.CYCLE_DOCTORAT);
                    break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                if (session != null){
                    if (((userInfo.getAccount() == false) && (userInfo.getMinervalles() == false)) && (userInfo.getEnrollementPremiereSession() == false)){
                        System.out.println("Vous n'avez pas finaliser avec vos minervalles et/ou votre enrollement");
                        messageSending.sendMessage(userInfo.getTelephone(),"Bonjour "+userInfo.getFirstName()+" "+userInfo.getLastName()+" "+userInfo.getName()+" Desole! vous ne pouvez pas consulter vos note a partir de ce service car vous n'avez pas finaliser avec le paiement de vos minervalles et/ou votre enrollement");
                        session = new Session();
                    }
                }
                break;
            case "DEUXIEME_SESSION":
                switch (niveau){
                    case "G1":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.DEUXIEME_SESSION, Niveau.G1);
                    break;
                    case "G2":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.DEUXIEME_SESSION, Niveau.G2);
                    break;
                    case "G3":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.DEUXIEME_SESSION, Niveau.G3);
                    break;
                    case "PRE_LICENCE":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.DEUXIEME_SESSION, Niveau.PRE_LICENCE);
                        break;
                    case "L1":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.DEUXIEME_SESSION, Niveau.L1);
                    break;
                    case "L2":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.DEUXIEME_SESSION, Niveau.L2);
                    break;
                    case "CYCLE_DOCTORAT":
                        session = sessionRepository.findByUserInfo_MatriculeAndNameAndNiveau(matricule,Periode.DEUXIEME_SESSION, Niveau.CYCLE_DOCTORAT);
                    break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                if (session != null){
                    if ((userInfo.getMinervalles() == false) && (userInfo.getEnrollementDeuxiemeSession() == false)){
                        System.out.println("Vous n'avez pas finaliser avec vos minervalles et/ou votre enrollement");
                        messageSending.sendMessage(userInfo.getTelephone(),"Bonjour "+userInfo.getFirstName()+" "+userInfo.getLastName()+" "+userInfo.getName()+" Desole! vous ne pouvez pas consulter vos note a partir de ce service car vous n'avez pas finaliser avec le paiement de vos minervalles et/ou votre enrollement");
                        session = new Session();
                    }
                }
                break;
                default:
                    System.out.println("nothing to do");
                    break;
        }
        System.out.println(session.getName()+" -- "+session.getNiveau()+" -- "+session.getPercentage()+" -- "+session.getSessionDate());
        List<Note> notes = noteRepository.findAllBySession_Id(session.getId());
        String notesValues = "";
        String message = "";
        for (Note note: notes){

            notesValues = notesValues + ", "+note.getMatiere()+" : "+note.getNote()+"/"+note.getMax();

        }
        System.out.println(notesValues);
        message = "Resultats de la "+session.getName()+", en date du "+session.getSessionDate()+", "+session.getNiveau()+", les notes :"+notesValues+", Total :"+session.getTotal()+", Pourcentage : "+session.getPercentage()+", Nombre de cours echoues : "+session.getUvLostNumber()+", Decision finale : "+session.getDecision();
        messageSending.sendMessage("0976632858", message);
        System.out.println(message);
        return session;
    }

    public Session findByUserIdAndNameAndNiveau(Long id, String name, String niveau){
        Session session = new Session();
        switch (name){
            case "MI_SESSION":
                switch (niveau){
                    case "G1":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.MI_SESSION, Niveau.G1);
                        break;
                    case "G2":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.MI_SESSION, Niveau.G2);
                        break;
                    case "G3":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.MI_SESSION, Niveau.G3);
                        break;
                    case "PRE_LICENCE":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.MI_SESSION, Niveau.PRE_LICENCE);
                        break;
                    case "L1":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.MI_SESSION, Niveau.L1);
                        break;
                    case "L2":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.MI_SESSION, Niveau.L2);
                        break;
                    case "CYCLE_DOCTORAT":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.MI_SESSION, Niveau.CYCLE_DOCTORAT);
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                break;
            case "PREMIERE_SESSION":
                switch (niveau){
                    case "G1":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.PREMIERE_SESSION, Niveau.G1);
                        break;
                    case "G2":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.PREMIERE_SESSION, Niveau.G2);
                        break;
                    case "G3":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.PREMIERE_SESSION, Niveau.G3);
                        break;
                    case "PRE_LICENCE":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.PREMIERE_SESSION, Niveau.PRE_LICENCE);
                        break;
                    case "L1":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.PREMIERE_SESSION, Niveau.L1);
                        break;
                    case "L2":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.PREMIERE_SESSION, Niveau.L2);
                        break;
                    case "CYCLE_DOCTORAT":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.PREMIERE_SESSION, Niveau.CYCLE_DOCTORAT);
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                break;
            case "DEUXIEME_SESSION":
                switch (niveau){
                    case "G1":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.DEUXIEME_SESSION, Niveau.G1);
                        break;
                    case "G2":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.DEUXIEME_SESSION, Niveau.G2);
                        break;
                    case "G3":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.DEUXIEME_SESSION, Niveau.G3);
                        break;
                    case "PRE_LICENCE":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.DEUXIEME_SESSION, Niveau.PRE_LICENCE);
                        break;
                    case "L1":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.DEUXIEME_SESSION, Niveau.L1);
                        break;
                    case "L2":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.DEUXIEME_SESSION, Niveau.L2);
                        break;
                    case "CYCLE_DOCTORAT":
                        session = sessionRepository.findByUserInfo_IdAndNameAndNiveau(id,Periode.DEUXIEME_SESSION, Niveau.CYCLE_DOCTORAT);
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                break;
            default:
                System.out.println("nothing to do");
                break;
        }
        System.out.println(session.getName()+" -- "+session.getNiveau()+" -- "+session.getPercentage()+" -- "+session.getSessionDate());
        return session;
    }

    public List<Session> findAllByUserAndNiveau(Long id, String niveau){
        List<Session> sessions = new ArrayList<>();

        switch (niveau){
            case "G1":
                sessions = sessionRepository.findAllByUserInfo_IdAndNiveau(id, Niveau.G1);
                break;
            case "G2":
                sessions = sessionRepository.findAllByUserInfo_IdAndNiveau(id, Niveau.G2);
                break;
            case "G3":
                sessions = sessionRepository.findAllByUserInfo_IdAndNiveau(id, Niveau.G3);
                break;
            case "PRE_LICENCE":
                sessions = sessionRepository.findAllByUserInfo_IdAndNiveau(id, Niveau.PRE_LICENCE);
                break;
            case "L1":
                sessions = sessionRepository.findAllByUserInfo_IdAndNiveau(id, Niveau.L1);
                break;
            case "L2":
                sessions = sessionRepository.findAllByUserInfo_IdAndNiveau(id, Niveau.L2);
                break;
            case "CYCLE_DOCTORAT":
                sessions = sessionRepository.findAllByUserInfo_IdAndNiveau(id, Niveau.CYCLE_DOCTORAT);
                break;
            default:
                System.out.println("nothing to do");
                break;
        }
        return sessions;
    }

    public List<Note> getNoteBySession(Long id){
        return noteRepository.findAllBySession_Id(id);
    }

    public Session getOne(Long id){
        return sessionRepository.getOne(id);
    }
}
