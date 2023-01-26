package com.fis.app;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("integration")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-integration.properties")
public class DemoServiceTests {

	@LocalServerPort
	protected int port;

	protected StringBuilder getHost() {
		return new StringBuilder("http://localhost:").append(port);
	}

	public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:12.0")
			.withDatabaseName("postgres")
			.withUsername("postgres")
			.withPassword("postgres")
			//.withExposedPorts(5432)
			.withReuse(true); // activated this options to faster development test on your local system

	public static MockServerContainer mockServerContainer = new MockServerContainer(
			DockerImageName.parse("jamesdbloom/mockserver:mockserver-5.11.2")).withReuse(true);

	/*
	 * static block to starting container and will be used to workaround shutting
	 * down of container after each test class executed
	 */
	static {
		postgreDBContainer.start();
		mockServerContainer.start();
	}

	@DynamicPropertySource
	static void contextInitializerConfig(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",
				() -> String.format("jdbc:postgresql://localhost:%d/postgres", postgreDBContainer.getFirstMappedPort()));
		registry.add("spring.datasource.username", () -> "postgres");
		registry.add("spring.datasource.password", () -> "postgres");

		// config 3rd party url with mockserver
		registry.add("mock.server.endpoint", mockServerContainer::getEndpoint);
		registry.add("mockserver.base-url", () -> mockServerContainer.getEndpoint());
	}

	protected ObjectMapper mapper() {
		return new ObjectMapper();
	}

}
