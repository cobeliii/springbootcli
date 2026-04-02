package com.cobeliii.springbootcli.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CarServiceImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class CarServiceImplContainerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarServiceImpl underTest;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
    }

    @Test
    void itShouldGetAllAvailableCars() {
        //given
        Car car = new Car("BMW", "M5", "petrol");
        //when
        carRepository.save(car);
        List<CarDto> actual = underTest.getAllAvailableCars();

        //Assert
        assertThat(actual).containsOnly(new CarDto("BMW", "M5", "petrol"));
    }

    @Test
    void itShouldGetAllElectricCars() {
        //given
        Car car = new Car("BMW", "M5", "electric");
        carRepository.save(car);
        //when
        List<CarDto> actual = underTest.getAvailableElectricCars();
        //Assert
        assertThat(actual).containsOnly(new CarDto("BMW", "M5", "electric"));
    }

    @Test
    void itShouldGetCarById() {
        //given
        Car car = new Car("BMW", "M5", "electric");
        carRepository.save(car);
        //when
        Optional<CarDto> actual = underTest.getCarById(car.getId());
        //Assert
        assertThat(actual).isPresent().get().isEqualTo(new CarDto("BMW", "M5", "electric"));
    }
}
