package com.ocr.axa.jlp.paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableEncryptableProperties
@SpringBootApplication
public class PaymybuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymybuddyApplication.class, args);
    }

}
