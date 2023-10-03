package cleancode.cleancodestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CleancodeStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleancodeStudyApplication.class, args);
	}

}
