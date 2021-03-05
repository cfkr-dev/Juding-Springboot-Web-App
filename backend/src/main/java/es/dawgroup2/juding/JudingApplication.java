package es.dawgroup2.juding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class JudingApplication {
    // Main method of application
    public static void main(String[] args) {
        SpringApplication.run(JudingApplication.class, args);
    }

}
