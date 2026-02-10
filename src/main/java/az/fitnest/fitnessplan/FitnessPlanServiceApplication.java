package az.fitnest.fitnessplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FitnessPlanServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessPlanServiceApplication.class, args);
    }
}

