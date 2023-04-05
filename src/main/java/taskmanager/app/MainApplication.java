package taskmanager.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taskmanager.config.ApplicationConfig;

@SpringBootApplication
@ComponentScan(basePackages = "taskmanager")
@Import(ApplicationConfig.class)
@EntityScan(basePackages = {"taskmanager.model"})
@RestController
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

}

