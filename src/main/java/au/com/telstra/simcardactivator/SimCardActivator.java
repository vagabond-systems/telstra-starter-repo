package au.com.telstra.simcardactivator;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimCardActivator {

    public static void main(String[] args) {
        SpringApplication.run(SimCardActivator.class, args);
    }

//    public static void main(String[] args){
//        ConfigurableApplicationContext simContext = SpringApplication.run(SimCardActivator.class, "--spring.config.name=application-simcardactivator");
//
////        ConfigurableApplicationContext actuateContext = SpringApplication.run(ActuateServiceApplication.class, "--spring.config.name=application-actuate");


    @Bean
    public TomcatServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        // Main application runs on port 8080

        // Add second connector for ActuateService on port 8444
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(8444);
        factory.addAdditionalTomcatConnectors(connector);

        return factory;
    }

}
