package com.study.case_study_trans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CaseStudyTransApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseStudyTransApplication.class, args);
	}

}
