package com.cobeliii.springbootcli.booking;

import com.cobeliii.springbootcli.car.Car;
import com.cobeliii.springbootcli.car.CarRepository;
import com.cobeliii.springbootcli.user.User;
import com.cobeliii.springbootcli.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BookingServiceImpl.class)
@Testcontainers
class BookingServiceImplContainerTest {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingServiceImpl underTest;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        carRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Disabled
    void itShouldBookCar() {
        //given
        //when
        //Assert
    }

    @Test
    @Disabled
    void itShouldCancelBookingById() {
        //given
        //when
        //Assert
    }

    @Test
    void itShouldGetAllBookings() {
        //given
        Car car = new Car("BMW", "M5", "electric");
        carRepository.save(car);
        User user = new User("John");
        userRepository.save(user);
        Booking booking = new Booking(user, car);
        bookingRepository.save(booking);
        //when
        List<BookingDto> allBookings = underTest.getAllBookings();
        //Assert
        assertThat(allBookings)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("startTime, endTime").containsOnly(new BookingDto(booking.getId(),
                user.getId(),
                car.getId(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.isActive()));
    }
}