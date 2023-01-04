package kfq.springcoco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringCocoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCocoApplication.class, args);
	}

}
