package com.cobeliii.springbootcli.car;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        Pageable pageable = PageRequest.of(0, 10);

        when(carRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(car), pageable, 1));

        List<CarDto> actual = underTest.getAllAvailableCars();

        assertThat(actual).isEqualTo(List.of(new CarDto("BMW", "M5", "petrol")));
    }

    @Test
    void itShouldGetAllElectricCars() {
        Car car = new Car("BMW", "M5", "electric");
        Pageable pageable = PageRequest.of(0, 10);

        when(carRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(car), pageable, 1));

        List<CarDto> actual = underTest.getAllAvailableCars();
        assertThat(actual).isEqualTo(List.of(new CarDto("BMW", "M5", "electric")));
    }

    @Test
    void itShouldGetCarById(){
        Car car = new Car("BMW", "M5", "electric");
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        Optional<CarDto> actual = underTest.getCarById(car.getId());
        assertThat(actual).isEqualTo(Optional.of(new CarDto("BMW", "M5", "electric")));
    }
}
