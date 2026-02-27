package com.cobeliii.springbootcli.car;

import com.cobeliii.springbootcli.exceptions.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<CarDto> getAllAvailableCars() {
        return carRepository.findAll()
                .stream()
                .map(car -> new CarDto(car.getBrand(), car.getModel(), car.getEngineType()))
                .toList();
    }

    @Override
    public List<CarDto> getAvailableElectricCars() {
        return carRepository.findAll()
                .stream()
                .filter(car -> car.getEngineType().equalsIgnoreCase("electric"))
                .map(car -> new CarDto(car.getBrand(), car.getModel(), car.getEngineType()))
                .toList();
    }

    @Override
    public Optional<CarDto> getCarById(Long id) {
       return Optional.ofNullable(carRepository.findById(id)
               .map(value -> new CarDto(value.getBrand(), value.getModel(), value.getEngineType()))
               .orElseThrow(() -> new CarNotFoundException("Car was not found.")));

    }
}
