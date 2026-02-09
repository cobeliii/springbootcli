package com.cobeliii.springbootcli.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Car> allAvailableCars = carRepository.findAll();
        List<CarDto> allAvailableCarsDto = new ArrayList<>();
        for (Car car : allAvailableCars) {
            allAvailableCarsDto.add(new CarDto(car.getBrand(), car.getModel(), car.getEngineType()));
        }
        return allAvailableCarsDto;
    }

    @Override
    public List<CarDto> getAvailableElectricCars() {
        List<Car> electricCars = carRepository.findAll()
                .stream()
                .filter(car -> car.getEngineType().equalsIgnoreCase("electric"))
                .toList();

        List<CarDto> electricCarsDto = new ArrayList<>();
        for (Car car : electricCars) {
            electricCarsDto.add(new CarDto(car.getBrand(), car.getModel(), car.getEngineType()));
        }
        return electricCarsDto;
    }

    @Override
    public Optional<CarDto> getCarById(Long id) {
        Optional<Car> car = carRepository.findById(id);

        return car.map(value -> new CarDto(value.getBrand(), value.getModel(), value.getEngineType()));

    }
}
