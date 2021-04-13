package com.derteuffel.publicationNotes.services;

import com.derteuffel.publicationNotes.entities.Departement;
import com.derteuffel.publicationNotes.entities.Faculty;
import com.derteuffel.publicationNotes.repositories.DepartementRepository;
import com.derteuffel.publicationNotes.repositories.FacultyRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    public List<Departement> findAllByFaculty_Id(Long id) {
        return departementRepository.findAllByFaculty_Id(id, Sort.by(Sort.Direction.ASC,"name"));
    }

    public List<Departement> findAll() {
        return departementRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
    }

    public Departement getOne(Long id) {
        return departementRepository.getOne(id);
    }

    public void save(String filename, Long id) throws IOException {
        Faculty faculty = facultyRepository.getOne(id);
        FileInputStream file = new FileInputStream(new File(filename));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet departementSheet = workbook.getSheetAt(0);
        // save departements

        for (int i=5; i<= (departementSheet.getPhysicalNumberOfRows()-1); i++) {
            Departement departement = new Departement();
            Departement existedDepartementbyName = departementRepository.findByNameOrSlug(departementSheet.getRow(i).getCell(2).getStringCellValue(), departementSheet.getRow(i).getCell(2).getStringCellValue());
            Departement existedDepartementbySlug = departementRepository.findByNameOrSlug(departementSheet.getRow(i).getCell(2).getStringCellValue(), departementSheet.getRow(i).getCell(2).getStringCellValue());

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
    }

    public Departement updateDepartement(Departement departement, Long id){
        Departement existedDepartement = departementRepository.getOne(id);
        if (existedDepartement != null){
            existedDepartement.setName(departement.getName());
            existedDepartement.setSlug(departement.getSlug());
            departementRepository.save(departement);
        }
        return existedDepartement;
    }

    public void deleteById(Long aLong) {
        departementRepository.deleteById(aLong);
    }

    public Departement findByNameOrSlug(String name, String slug) {
        return departementRepository.findByNameOrSlug(name, slug);
    }
}
