package com.cobeliii.springbootcli.car;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public List<CarDto> getAllCars(){
        return carService.getAllAvailableCars();
    }

    @GetMapping("/electric")
    public List<CarDto> getAllElectricCars(){
        return carService.getAvailableElectricCars();
    }

    @GetMapping("/{id}")
    public Optional<CarDto> getCarById(@PathVariable Long id){
        return carService.getCarById(id);
    }

}
