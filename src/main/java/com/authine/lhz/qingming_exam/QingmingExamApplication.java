package com.authine.lhz.qingming_exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
public class QingmingExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingmingExamApplication.class, args);
    }

}
