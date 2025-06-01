package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		/*for (var provider : Security.getProviders()) {
			System.out.println(provider.getName());
			provider.getServices().stream()
					.filter(s -> s.getType().equals("Mac"))
					.forEach(s -> System.out.println(" - " + s.getAlgorithm()));
		}

		System.out.println("Java Version: " + System.getProperty("java.version"));
		System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
*/
		/*Properties properties = new Properties();
		System.setProperties(properties);*/

		/*System.getProperties().entrySet().stream().forEach(a-> {
			System.out.println(a.getKey() + " : " + a.getValue());
		});*/
		SpringApplication.run(DemoApplication.class, args);
	}

}
