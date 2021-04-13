package com.derteuffel.publicationNotes.services;

import com.derteuffel.publicationNotes.entities.Departement;
import com.derteuffel.publicationNotes.entities.Options;
import com.derteuffel.publicationNotes.repositories.DepartementRepository;
import com.derteuffel.publicationNotes.repositories.OptionsRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class OptionsService {

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private DepartementRepository departementRepository;

    public List<Options> findAllByDepartement_Id(Long id) {
        return optionsRepository.findAllByDepartement_Id(id);
    }

    public List<Options> findAll() {
        return optionsRepository.findAll();
    }

    public Options getOne(Long id) {
        return optionsRepository.getOne(id);
    }

    public void saveOption(String filename, Long id) throws IOException {

        FileInputStream file = new FileInputStream(new File(filename));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet optionsSheet = workbook.getSheetAt(0);
        // save Options

        for (int i=5; i<= (optionsSheet.getPhysicalNumberOfRows()-1); i++) {
            Options options = new Options();
            Options existedOptionsByName = optionsRepository.findByNameOrSlug(optionsSheet.getRow(i).getCell(1).getStringCellValue(), optionsSheet.getRow(i).getCell(1).getStringCellValue());
            Options existedOptionsBySlug = optionsRepository.findByNameOrSlug(optionsSheet.getRow(i).getCell(2).getStringCellValue(), optionsSheet.getRow(i).getCell(2).getStringCellValue());
            Departement departement = departementRepository.getOne(id);
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

    public void deleteById(Long id) {
        optionsRepository.deleteById(id);
    }

    public Options findByNameOrSlug(String name, String slug) {
        return optionsRepository.findByNameOrSlug(name, slug);
    }
}
