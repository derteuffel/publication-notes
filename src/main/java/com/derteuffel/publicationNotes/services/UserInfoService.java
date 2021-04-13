package com.derteuffel.publicationNotes.services;

import com.derteuffel.publicationNotes.entities.User;
import com.derteuffel.publicationNotes.entities.UserInfo;
import com.derteuffel.publicationNotes.enums.Niveau;
import com.derteuffel.publicationNotes.enums.Role;
import com.derteuffel.publicationNotes.enums.Sexe;
import com.derteuffel.publicationNotes.helpers.Generations;
import com.derteuffel.publicationNotes.helpers.UserInfoDto;
import com.derteuffel.publicationNotes.repositories.UserInfoRepository;
import com.derteuffel.publicationNotes.repositories.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserInfo save(UserInfoDto userInfo){
        UserInfo  newUser = new UserInfo(
        );
        newUser.setEmail(userInfo.getEmail());
        newUser.setMatricule(userInfo.getMatricule());
        newUser.setTelephone(userInfo.getTelephone());
        newUser.setNumIdentite(userInfo.getNumIdentite());
        newUser.setLastName(userInfo.getLastName());
        newUser.setInstitution(userInfo.getInstitution());
        newUser.setFirstName(userInfo.getFirstName());
        newUser.setAdressePhysique(userInfo.getAdressePhysique());
        newUser.setName(userInfo.getName());
        newUser.setBirthDate(userInfo.getBirthDate());
        newUser.setMinervalles(false);
        newUser.setEnrollementPremiereSession(false);
        newUser.setEnrollementMiSession(false);
        newUser.setEnrollementDeuxiemeSession(false);
        newUser.setAccount(false);
        if (userInfo.getSexe().equals("M")){
            newUser.setSexe(Sexe.MASCULIN);
        }else if (userInfo.getSexe().equals("F")){
            newUser.setSexe(Sexe.FEMININ);
        }else {
            newUser.setSexe(Sexe.NON_DEFINI);
        }

        switch (userInfo.getSexe()){
            case "G1":
                newUser.setNiveau(Niveau.G1);
                break;
            case "G2":
                newUser.setNiveau(Niveau.G2);
                break;
            case "G3":
                newUser.setNiveau(Niveau.G3);
                break;
            case "PRE_LICENCE":
                newUser.setNiveau(Niveau.PRE_LICENCE);
                break;
            case "L1":
                newUser.setNiveau(Niveau.L1);
                break;
            case "L2":
                newUser.setNiveau(Niveau.L2);
                break;
            case "M1":
                newUser.setNiveau(Niveau.M1);
                break;
            case "M2":
                newUser.setNiveau(Niveau.M2);
                break;
                default:
                    newUser.setNiveau(Niveau.CYCLE_DOCTORAT);
        }


        userInfoRepository.save(newUser);
        User user = new User();
        user.setActive(false);
        user.setEmail(userInfo.getEmail());
        user.setPassword(passwordEncoder.encode(userInfo.getMatricule()));

        user.setRole(Role.ETUDIANT);
        user.setUsername(userInfo.getMatricule());
        user.setCreatedDate(new Date());
        user.setFonction("Etudiant");
        user.setUserInfo(newUser);
        userRepository.save(user);
        return newUser;
    }

    public UserInfo saveForAccount(UserInfoDto userInfo){
        UserInfo  newUser = new UserInfo(
        );
        newUser.setEmail(userInfo.getEmail());
        newUser.setMatricule(userInfo.getMatricule());
        newUser.setTelephone(userInfo.getTelephone());
        newUser.setNumIdentite(userInfo.getNumIdentite());
        newUser.setLastName(userInfo.getLastName());
        newUser.setInstitution(userInfo.getInstitution());
        newUser.setFirstName(userInfo.getFirstName());
        newUser.setAdressePhysique(userInfo.getAdressePhysique());
        newUser.setName(userInfo.getName());
        newUser.setBirthDate(userInfo.getBirthDate());
        newUser.setMinervalles(false);
        newUser.setEnrollementPremiereSession(false);
        newUser.setEnrollementMiSession(false);
        newUser.setEnrollementDeuxiemeSession(false);
        newUser.setAccount(false);
        if (userInfo.getSexe().equals("M")){
            newUser.setSexe(Sexe.MASCULIN);
        }else if (userInfo.getSexe().equals("F")){
            newUser.setSexe(Sexe.FEMININ);
        }else {
            newUser.setSexe(Sexe.NON_DEFINI);
        }

        switch (userInfo.getSexe()){
            case "G1":
                newUser.setNiveau(Niveau.G1);
                break;
            case "G2":
                newUser.setNiveau(Niveau.G2);
                break;
            case "G3":
                newUser.setNiveau(Niveau.G3);
                break;
            case "PRE_LICENCE":
                newUser.setNiveau(Niveau.PRE_LICENCE);
                break;
            case "L1":
                newUser.setNiveau(Niveau.L1);
                break;
            case "L2":
                newUser.setNiveau(Niveau.L2);
                break;
            case "M1":
                newUser.setNiveau(Niveau.M1);
                break;
            case "M2":
                newUser.setNiveau(Niveau.M2);
                break;
            default:
                newUser.setNiveau(Niveau.CYCLE_DOCTORAT);
        }


        userInfoRepository.save(newUser);

        User user = new User();
        switch (userInfo.getRole()) {
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
        user.setActive(false);
        user.setEmail(userInfo.getEmail());
        user.setPassword(passwordEncoder.encode(userInfo.getMatricule()));
        user.setUsername(userInfo.getMatricule());
        user.setCreatedDate(new Date());
        user.setFonction(userInfo.getFonction());
        user.setUserInfo(newUser);
        userRepository.save(user);
        return newUser;
    }

    public UserInfo getOne(Long id){
        return  userInfoRepository.getOne(id);
    }

    public List<UserInfo> findAll(){
        return userInfoRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public void delete(Long id){
        userInfoRepository.deleteById(id);
    }

    public UserInfo update(UserInfo userInfo, Long id){
        UserInfo userInfo1 = userInfoRepository.getOne(id);
        userInfo1.setName(userInfo.getName());
        userInfo1.setAdressePhysique(userInfo.getAdressePhysique());
        userInfo1.setFirstName(userInfo.getFirstName());
        userInfo1.setInstitution(userInfo.getInstitution());
        userInfo1.setBirthDate(userInfo.getBirthDate());
        userInfo1.setLastName(userInfo.getLastName());
        userInfo1.setNiveau(userInfo.getNiveau());
        userInfo1.setNumIdentite(userInfo.getNumIdentite());
        userInfo1.setSexe(userInfo.getSexe());
        userInfo1.setTelephone(userInfo.getTelephone());
        User user = userRepository.findByUsername(userInfo1.getMatricule()).get();
        if (!(userInfo.getMatricule().equals(user.getUsername()))){
            userInfo1.setMatricule(userInfo.getMatricule());
            user.setUsername(userInfo.getMatricule());
            user.setPassword(passwordEncoder.encode(userInfo.getMatricule()));
        }

        if (!(userInfo.getEmail().equals(user.getEmail()))){
            userInfo1.setEmail(userInfo.getEmail());
            user.setEmail(userInfo.getEmail());
        }

        userInfoRepository.save(userInfo1);
        userRepository.save(user);
        return userInfo1;
    }

    public  void saveUsers(String filename) throws IOException {

        Generations generations = new Generations();
        FileInputStream file = new FileInputStream(new File(filename));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for (Cell cell : row) {
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        System.out.println("j'ai pour indice :"+i);
                        System.out.println(data.get(new Integer(i)).add(cell.getRichStringCellValue().getString()));
                         break;
                    case NUMERIC:
                        System.out.println("j'ai pour indice :"+i);
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.println(data.get(i).add(cell.getDateCellValue() + ""));
                        } else {
                            System.out.println(data.get(i).add(cell.getNumericCellValue() + ""));
                        }
                         break;
                    case BOOLEAN:
                        System.out.println("j'ai pour indice :"+i);
                        System.out.println(data.get(i).add(cell.getBooleanCellValue() + ""));
                        break;
                    case FORMULA:
                        System.out.println("j'ai pour indice :"+i);
                        System.out.println(data.get(i).add(cell.getCellFormula() + ""));
                        break;
                    default: data.get(new Integer(i)).add(" ");
                }
            }
            i++;
        }
        System.out.println(data.size());
        System.out.println(data.get(1));
        System.out.println(data.get(2));
        System.out.println(data.get(2).get(0));
        for (int j=1; j<=(data.size()-1); j++) {
            User user = new User();
            UserInfo userInfo = new UserInfo();
            //Pass User Info values
            userInfo.setNiveau(Niveau.G1);
            if (data.get(j).get(5).equals("masculin")) {
                userInfo.setSexe(Sexe.MASCULIN);
            }else if (data.get(j).get(5).equals("Feminin")){
                userInfo.setSexe(Sexe.FEMININ);
            }else {
                userInfo.setSexe(Sexe.NON_DEFINI);
            }
            switch (data.get(j).get(11)){
                case "G1":
                    userInfo.setNiveau(Niveau.G1);
                    break;
                case "G2":
                    userInfo.setNiveau(Niveau.G2);
                    break;
                case "G3":
                    userInfo.setNiveau(Niveau.G3);
                    break;
                case "PRE_LICENCE":
                    userInfo.setNiveau(Niveau.PRE_LICENCE);
                    break;
                case "L1":
                    userInfo.setNiveau(Niveau.L1);
                    break;
                case "L2":
                    userInfo.setNiveau(Niveau.L2);
                    break;
                case "M1":
                    userInfo.setNiveau(Niveau.M1);
                    break;
                default:
                    userInfo.setNiveau(Niveau.CYCLE_DOCTORAT);

            }
            userInfo.setBirthDate(data.get(j).get(4));
            userInfo.setName(data.get(j).get(1));
            userInfo.setAdressePhysique(data.get(j).get(9));
            userInfo.setFirstName(data.get(j).get(2));
            userInfo.setInstitution(data.get(j).get(10));
            userInfo.setLastName(data.get(j).get(3));
            userInfo.setNumIdentite(Double.valueOf(""+data.get(j).get(8)).longValue()+"");
            userInfo.setTelephone(Double.valueOf(""+data.get(j).get(7)).longValue()+"");
            userInfo.setMatricule(Double.valueOf(""+data.get(j).get(0)).longValue()+"");
            userInfo.setEmail(data.get(j).get(6));
            userInfo.setMinervalles(false);
            userInfo.setEnrollementPremiereSession(false);
            userInfo.setEnrollementMiSession(false);
            userInfo.setEnrollementDeuxiemeSession(false);
            userInfo.setAccount(false);
            userInfoRepository.save(userInfo);

            // Pass account values
            user.setEmail(userInfo.getEmail());
            user.setPassword(passwordEncoder.encode(userInfo.getMatricule()));
            user.setUsername(userInfo.getMatricule());
            user.setFonction("Etudiant");
            user.setCreatedDate(new Date());
            user.setRole(Role.ETUDIANT);
            user.setActive(false);
            user.setCode(generations.generatedString());
            user.setUserInfo(userInfo);

            userRepository.save(user);
        }
    }

    public List<UserInfo> getAllByOptions(Long id){
        return userInfoRepository.findAllByOptions_Id(id, Sort.by(Sort.Direction.ASC, "name"));
    }
}
