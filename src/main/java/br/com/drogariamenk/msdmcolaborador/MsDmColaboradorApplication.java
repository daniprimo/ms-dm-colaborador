package br.com.drogariamenk.msdmcolaborador;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
public class MsDmColaboradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDmColaboradorApplication.class, args);
	}

}
