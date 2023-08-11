package smu.likelion.kkyong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KkyongApplication {

	public static void main(String[] args) {
		SpringApplication.run(KkyongApplication.class, args);
	}

}
