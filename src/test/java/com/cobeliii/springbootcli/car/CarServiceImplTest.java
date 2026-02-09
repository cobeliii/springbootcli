package com.cobeliii.springbootcli.car;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl underTest;

    @Test
    void itShouldGetAllCars() {
        Car car = new Car("BMW", "M5", "petrol");
        when(carRepository.findAll()).thenReturn(List.of(car));
        List<CarDto> cars = underTest.getAllAvailableCars();
        assertThat(cars).isEqualTo(List.of(new CarDto("BMW", "M5", "petrol")));
    }

    @Test
    void itShouldGetAllElectricCars() {
        Car car = new Car("BMW", "M5", "electric");
        when(carRepository.findAll()).thenReturn(List.of(car));
        List<CarDto> cars = underTest.getAllAvailableCars();
        assertThat(cars).isEqualTo(List.of(new CarDto("BMW", "M5", "electric")));
    }

    @Test
    void itShouldGetCarById(){
        Car car = new Car("BMW", "M5", "electric");
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        Optional<CarDto> carDto = underTest.getCarById(car.getId());
        assertThat(carDto).isEqualTo(Optional.of(new CarDto("BMW", "M5", "electric")));
    }
}
