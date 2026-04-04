package com.cobeliii.springbootcli.journey;

import com.cobeliii.springbootcli.booking.Booking;
import com.cobeliii.springbootcli.booking.BookingDto;
import com.cobeliii.springbootcli.booking.BookingRepository;
import com.cobeliii.springbootcli.booking.BookingRequest;
import com.cobeliii.springbootcli.car.Car;
import com.cobeliii.springbootcli.car.CarRepository;
import com.cobeliii.springbootcli.user.User;
import com.cobeliii.springbootcli.user.UserRepository;
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
public class BookingIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        carRepository.deleteAll();
        bookingRepository.deleteAll();

        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void itShouldGetAllBookings() {
        Car car = new Car("BMW", "M5", "petrol");
        User user = new User("John");

        carRepository.save(car);
        userRepository.save(user);
        Booking booking = new Booking(user, car);
        bookingRepository.save(booking);

        List<BookingDto> responseBody = webTestClient.get()
                .uri("api/v1/bookings")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<BookingDto>() {
                }).returnResult()
                .getResponseBody();

        assertThat(responseBody)
                .isNotEmpty()
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("startTime", "endTime")
                .containsOnly(new BookingDto(
                        booking.getId(),
                        user.getId(),
                        car.getId(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        booking.isActive()));
    }

    @Test
    void itShouldBookCar() {
        Car car = new Car("BMW", "M5", "petrol");
        User user = new User("John");
        carRepository.save(car);
        userRepository.save(user);

        BookingRequest request = new BookingRequest(user.getId(), car.getId());

        // when
        Booking responseBody = webTestClient.post()
                .uri("/api/v1/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()          // 201
                .expectBody(Booking.class)
                .returnResult()
                .getResponseBody();

        System.out.println(responseBody);
        // then
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getUser().getId()).isEqualTo(user.getId());
        assertThat(responseBody.getCar().getId()).isEqualTo(car.getId());
        assertThat(bookingRepository.findAll()).hasSize(1);
    }

    @Test
    void itShouldCancelBookingById() {
        //given
        //when
        //Assert
    }
}
