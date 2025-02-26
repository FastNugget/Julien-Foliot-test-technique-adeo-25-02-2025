package com.yaourt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j(topic = "MainApplication")
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class MainApplication {


	// -- ENTRY POINT --------------------------------------------------------------------------------------------------

	public static void main(String[] arrayStringArgs){

		// -- Start app
		SpringApplication.run(MainApplication.class, arrayStringArgs);

		// -- Log
		log.info("YaourtErp-ApiDomain ! {}", ":)");
		log.info("UI LINK --> http://localhost:8080/swagger-ui/index.html#/", "");
		log.info("OPEN-API-SPEC LINK --> JSON --> http://localhost:8080/api-docs", "");
		log.info("OPEN-API-SPEC LINK --> YAML --> http://localhost:8080/api-docs.yaml", "");

	}


}