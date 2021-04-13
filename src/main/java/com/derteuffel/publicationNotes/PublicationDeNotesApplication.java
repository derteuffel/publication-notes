package com.derteuffel.publicationNotes;

import com.derteuffel.publicationNotes.helpers.UserInfoDto;
import com.derteuffel.publicationNotes.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication(scanBasePackages = "com.derteuffel.publicationNotes" )
public class PublicationDeNotesApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
	private UserInfoService userInfoService;

	public static void main(String[] args) {
		SpringApplication.run(PublicationDeNotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

       /* UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setAdressePhysique("Kinshasa");
        userInfoDto.setBirthDate("01/03/1994");
        userInfoDto.setEmail("derteuffel0@gmail.com");
        userInfoDto.setFirstName("Stephane");
        userInfoDto.setFonction("Root master");
        userInfoDto.setInstitution("ENSPM Maroua Cameroun");
        userInfoDto.setLastName("derteuffel");
        userInfoDto.setMatricule("14A576S");
        userInfoDto.setName("afana");
        userInfoDto.setSexe("Masculin");
        userInfoDto.setNiveau("M2");
        userInfoDto.setNumIdentite("11345399969");
        userInfoDto.setTelephone("+243976632858");
        userInfoDto.setRole("ROOT");
        userInfoService.saveForAccount(userInfoDto);
*/
	}
}
