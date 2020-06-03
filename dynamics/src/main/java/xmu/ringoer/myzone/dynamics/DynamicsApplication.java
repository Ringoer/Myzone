package xmu.ringoer.myzone.dynamics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Ringoer
 */
@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class DynamicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicsApplication.class, args);
	}

}
