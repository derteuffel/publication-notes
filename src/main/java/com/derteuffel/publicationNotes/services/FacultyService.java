package com.derteuffel.publicationNotes.services;

import com.derteuffel.publicationNotes.entities.Departement;
import com.derteuffel.publicationNotes.entities.Faculty;
import com.derteuffel.publicationNotes.entities.Options;
import com.derteuffel.publicationNotes.repositories.DepartementRepository;
import com.derteuffel.publicationNotes.repositories.FacultyRepository;
import com.derteuffel.publicationNotes.repositories.OptionsRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private OptionsRepository optionsRepository;

    public List<Faculty> findAll() {
        return facultyRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
    }

    public Faculty getOne(Long aLong) {
        return facultyRepository.getOne(aLong);
    }

    public void update(Long id, Faculty faculty){
        Faculty existedFaculty = facultyRepository.getOne(id);
        if (existedFaculty != null){
            System.out.println(faculty.getAdressePhysique());
            existedFaculty.setAdressePhysique(faculty.getAdressePhysique());
            System.out.println(faculty.getEmail());
            existedFaculty.setEmail(faculty.getEmail());
            existedFaculty.setTelephone(faculty.getTelephone());
            existedFaculty.setSlug(faculty.getSlug());
            existedFaculty.setName(faculty.getName());
            facultyRepository.save(existedFaculty);
        }
    }

    public  void save(String filename) throws IOException {
        FileInputStream file = new FileInputStream(new File(filename));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Sheet departementSheet = workbook.getSheetAt(1);
        Sheet optionsSheet = workbook.getSheetAt(2);

        System.out.println(sheet.getPhysicalNumberOfRows());
        for (int i=5; i<= (sheet.getPhysicalNumberOfRows()-1); i++) {
            Faculty faculty = new Faculty();
            Faculty existedFacultyByname = facultyRepository.findByNameOrSlug(sheet.getRow(i).getCell(1).getStringCellValue(), sheet.getRow(i).getCell(1).getStringCellValue());
            Faculty existedFacultyBySlug = facultyRepository.findByNameOrSlug(sheet.getRow(i).getCell(2).getStringCellValue(), sheet.getRow(i).getCell(2).getStringCellValue());
            //test if existedFculty are not null
            if ((existedFacultyByname != null) || (existedFacultyBySlug != null)) {
                System.out.println("Cette faculte existe deja");
            } else {
                // adding faculty name
                switch (sheet.getRow(i).getCell(1).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(1).getStringCellValue());
                        faculty.setName(sheet.getRow(i).getCell(1).getStringCellValue());
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(1).getCellFormula());
                        faculty.setName(sheet.getRow(i).getCell(1).getCellFormula());
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }

                // adding faculty slug
                switch (sheet.getRow(i).getCell(2).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(2).getStringCellValue());
                        faculty.setSlug(sheet.getRow(i).getCell(2).getStringCellValue());
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(2).getCellFormula());
                        faculty.setSlug(sheet.getRow(i).getCell(2).getCellFormula());
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                // adding faculty phone
                switch (sheet.getRow(i).getCell(3).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(3).getStringCellValue());
                        faculty.setTelephone(Double.valueOf(sheet.getRow(i).getCell(3).getStringCellValue()).longValue() + "");
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(3).getCellFormula());
                        faculty.setTelephone(Double.valueOf(sheet.getRow(i).getCell(3).getCellFormula()).longValue() + "");
                        break;
                    case NUMERIC:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(3).getNumericCellValue());
                        faculty.setTelephone(Double.valueOf(sheet.getRow(i).getCell(3).getNumericCellValue()).longValue() + "");
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }

                // adding faculty email
                switch (sheet.getRow(i).getCell(4).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(4).getStringCellValue());
                        faculty.setEmail(sheet.getRow(i).getCell(4).getStringCellValue());
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(4).getCellFormula());
                        faculty.setEmail(sheet.getRow(i).getCell(4).getCellFormula());
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                // adding faculty physical adress
                switch (sheet.getRow(i).getCell(5).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(5).getStringCellValue());
                        faculty.setAdressePhysique(sheet.getRow(i).getCell(5).getStringCellValue());
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + sheet.getRow(i).getCell(5).getCellFormula());
                        faculty.setAdressePhysique(sheet.getRow(i).getCell(5).getCellFormula());
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                facultyRepository.save(faculty);
            }
        }

        // save departements

        for (int i=5; i<= (departementSheet.getPhysicalNumberOfRows()-1); i++) {
            Departement departement = new Departement();
            Departement existedDepartementbyName = departementRepository.findByNameOrSlug(departementSheet.getRow(i).getCell(2).getStringCellValue(), departementSheet.getRow(i).getCell(2).getStringCellValue());
            Departement existedDepartementbySlug = departementRepository.findByNameOrSlug(departementSheet.getRow(i).getCell(2).getStringCellValue(), departementSheet.getRow(i).getCell(2).getStringCellValue());
            Faculty faculty = facultyRepository.findByNameOrSlug(departementSheet.getRow(i).getCell(3).getStringCellValue(), departementSheet.getRow(i).getCell(3).getStringCellValue());

            if ((existedDepartementbyName != null) || (existedDepartementbySlug != null)) {
                System.out.println("Ce departement existe deja");
            } else {
                // adding departement name
                switch (departementSheet.getRow(i).getCell(1).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + departementSheet.getRow(i).getCell(1).getStringCellValue());
                        departement.setName(departementSheet.getRow(i).getCell(1).getStringCellValue());
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + departementSheet.getRow(i).getCell(1).getCellFormula());
                        departement.setName(departementSheet.getRow(i).getCell(1).getCellFormula());
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                // adding departement slug
                switch (departementSheet.getRow(i).getCell(2).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + departementSheet.getRow(i).getCell(2).getStringCellValue());
                        departement.setSlug(departementSheet.getRow(i).getCell(2).getStringCellValue());
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + departementSheet.getRow(i).getCell(2).getCellFormula());
                        departement.setSlug(departementSheet.getRow(i).getCell(2).getCellFormula());
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                departement.setFaculty(faculty);
                departementRepository.save(departement);

            }
        }

        // save Options

        for (int i=5; i<= (optionsSheet.getPhysicalNumberOfRows()-1); i++) {
            Options options = new Options();
            Options existedOptionsByName = optionsRepository.findByNameOrSlug(optionsSheet.getRow(i).getCell(1).getStringCellValue(), optionsSheet.getRow(i).getCell(1).getStringCellValue());
            Options existedOptionsBySlug = optionsRepository.findByNameOrSlug(optionsSheet.getRow(i).getCell(2).getStringCellValue(), optionsSheet.getRow(i).getCell(2).getStringCellValue());
            Departement departement = departementRepository.findByNameOrSlug(optionsSheet.getRow(i).getCell(3).getStringCellValue(), optionsSheet.getRow(i).getCell(3).getStringCellValue());

            if ((existedOptionsByName != null) || (existedOptionsBySlug != null)) {
                System.out.println(" Cette Options existe deja");
            } else {

                // adding options name
                switch (optionsSheet.getRow(i).getCell(1).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + optionsSheet.getRow(i).getCell(1).getStringCellValue());
                        options.setName(optionsSheet.getRow(i).getCell(1).getStringCellValue());
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + optionsSheet.getRow(i).getCell(1).getCellFormula());
                        options.setName(optionsSheet.getRow(i).getCell(1).getCellFormula());
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                // adding departement slug
                switch (optionsSheet.getRow(i).getCell(2).getCellTypeEnum()) {
                    case STRING:
                        System.out.println("je contient :" + optionsSheet.getRow(i).getCell(2).getStringCellValue());
                        options.setSlug(optionsSheet.getRow(i).getCell(2).getStringCellValue());
                        break;
                    case FORMULA:
                        System.out.println("je contient :" + optionsSheet.getRow(i).getCell(2).getCellFormula());
                        options.setSlug(optionsSheet.getRow(i).getCell(2).getCellFormula());
                        break;
                    default:
                        System.out.println("nothing to do");
                        break;
                }
                options.setDepartement(departement);
                optionsRepository.save(options);

            }
        }
    }

    public void deleteById(Long aLong) {
        facultyRepository.deleteById(aLong);
    }

    public Faculty findByNameOrSlug(String name, String slug) {
        return facultyRepository.findByNameOrSlug(name, slug);
    }

    public Faculty findByUser_Id(Long id) {
        return facultyRepository.findByUser_Id(id);
    }
}
