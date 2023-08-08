package com.example.hwspringboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HwSpringBootApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	@Container
	private static final GenericContainer<?> myAppDev = new GenericContainer<>("devapp")
			.withExposedPorts(8080);
	@Container
	private static final GenericContainer<?> myAppProd = new GenericContainer<>("prodapp")
			.withExposedPorts(8081);

	@BeforeAll
	public static void setUp() {
		myAppDev.start();
		myAppProd.start();
	}

	@Test
	void contextLoadsDevApp() {
		ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:"
				+ myAppDev.getMappedPort(8080) + "/profile", String.class);
		Assertions.assertEquals("Current profile is dev", forEntity.getBody());
		System.out.println(forEntity.getBody());
	}

	@Test
	void contextLoadsProdApp() {
		ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:"
				+ myAppProd.getMappedPort(8081) + "/profile", String.class);
		Assertions.assertEquals("Current profile is production", forEntity.getBody());
		System.out.println(forEntity.getBody());
	}

}