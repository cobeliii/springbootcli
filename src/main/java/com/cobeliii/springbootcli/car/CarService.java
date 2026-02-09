package com.cobeliii.springbootcli.car;

import java.util.List;
import java.util.Optional;

public interface CarService{
    List<CarDto> getAllAvailableCars();
    List<CarDto> getAvailableElectricCars();
    Optional<CarDto> getCarById(Long id);
}
