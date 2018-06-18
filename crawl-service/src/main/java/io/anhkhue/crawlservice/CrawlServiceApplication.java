package io.anhkhue.crawlservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletContext;
import java.io.File;

@SpringBootApplication
public class CrawlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            File file = new ClassPathResource("studentAccounts.xml").getFile();
            System.out.println(file.getAbsolutePath());
        };
    }
}
