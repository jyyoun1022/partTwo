package springboot.partTwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PartTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartTwoApplication.class, args);
	}

}
