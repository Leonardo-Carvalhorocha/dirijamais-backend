package dirijamais.project.dirijamais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DirijamaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirijamaisApplication.class, args);
	}

}
