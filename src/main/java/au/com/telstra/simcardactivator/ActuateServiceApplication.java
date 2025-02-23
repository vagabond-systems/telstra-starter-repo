package au.com.telstra.simcardactivator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ActuateServiceApplication {

    @PostConstruct
    public void startActuateService(){
        System.out.println("ActuateService started on port 8444 inside SimCardActivator process.");
    }
}
