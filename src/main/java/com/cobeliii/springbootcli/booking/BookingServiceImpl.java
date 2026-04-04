package com.cobeliii.springbootcli.booking;

import com.cobeliii.springbootcli.car.Car;
import com.cobeliii.springbootcli.car.CarRepository;
import com.cobeliii.springbootcli.exceptions.BookingFailedException;
import com.cobeliii.springbootcli.exceptions.CarNotFoundException;
import com.cobeliii.springbootcli.exceptions.UserNotFoundException;
import com.cobeliii.springbootcli.user.User;
import com.cobeliii.springbootcli.user.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, CarRepository carRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Booking bookCar(Long carId, Long userId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (bookingRepository.existsByUserIdAndCarIdAndIsActiveTrue(userId, carId)) {
            throw new BookingFailedException("User already booked this car");
        }

        if (bookingRepository.countByUserIdAndIsActiveTrue(userId) >= 2) {
            throw new BookingFailedException("User can only book 2 cars");
        }

        if (!car.isAvailable()) {
            throw new BookingFailedException("Car is not available");
        }

        car.setAvailable(false);
        carRepository.save(car);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setStartTime(LocalDateTime.now());
        booking.setActive(true);
        return bookingRepository.save(booking);
    }

    @Override
    public void cancelBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setEndTime(LocalDateTime.now());

        Car car = booking.getCar();
        if (car != null){
            car.setAvailable(true);
            carRepository.save(car);
        }

        booking.setActive(false);
        bookingRepository.deleteById(bookingId);
    }



    @Override
    public List<BookingDto> getAllBookings() {
        Pageable pageable = PageRequest.of(0, 10);
        return bookingRepository.findAll(pageable)
                .stream()
                .map(b -> new BookingDto(
                        b.getId(),
                        b.getUser() != null ? b.getUser().getId() : null,
                        b.getCar() != null ? b.getCar().getId() : null,
                        b.getStartTime(),
                        b.getEndTime(),
                        b.isActive()
                ))
                .toList();
    }

}
