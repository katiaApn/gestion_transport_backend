package apn.gov.aeroport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AeroportApplication {


	public static void main(String[] args) {
		SpringApplication.run(AeroportApplication.class, args);
	}

}
