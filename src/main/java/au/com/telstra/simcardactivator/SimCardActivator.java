package au.com.telstra.simcardactivator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SimCardActivator {

//    public static void main(String[] args) {
//        SpringApplication.run(SimCardActivator.class, args);
//    }

    public static void main(String[] args){
        ConfigurableApplicationContext iccidContext = SpringApplication.run(SimCardActivator.class, "--spring.config.name=application-simcardactivator");

        ConfigurableApplicationContext actuateContext = SpringApplication.run(ActuateServiceApplication.class, "--spring.config.name= application-actuate");
    }


}
