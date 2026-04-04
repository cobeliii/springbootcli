package com.cobeliii.springbootcli.journey;

import com.cobeliii.springbootcli.car.Car;
import com.cobeliii.springbootcli.car.CarDto;
import com.cobeliii.springbootcli.car.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
public class CarIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @LocalServerPort
    private int port;

    @Autowired
    private CarRepository carRepository;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();

        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void itShouldGetAllCars() {
        Car car = new Car("BMW", "M5", "petrol");
        carRepository.save(car);
        List<CarDto> responseBody = webTestClient.get()
                .uri("/api/v1/cars")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<CarDto>() {
                }).returnResult()
                .getResponseBody();
        //Assert

        assertThat(responseBody).isNotEmpty()
                .containsOnly(new CarDto(car.getBrand(), car.getModel(), car.getEngineType()));
    }

    @Test
    void itShouldGetAllElectricCars() {
        Car car = new Car("BMW", "M5", "electric");
        carRepository.save(car);
        List<CarDto> responseBody = webTestClient.get()
                .uri("/api/v1/cars/electric")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<CarDto>() {
                }).returnResult()
                .getResponseBody();
        //Assert

        assertThat(responseBody).isNotEmpty()
                .containsOnly(new CarDto(car.getBrand(), car.getModel(), car.getEngineType()));
    }

    @Test
    void itShouldGetCarById() {
        Car car = new Car("BMW", "M5", "electric");
        carRepository.save(car);
        CarDto responseBody = webTestClient.get()
                .uri("/api/v1/cars" + "/" + car.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDto.class)
                .returnResult()
                .getResponseBody();
        //Assert

        assertThat(responseBody).isNotNull()
                .isEqualTo(new CarDto(car.getBrand(), car.getModel(), car.getEngineType()));
    }
}
