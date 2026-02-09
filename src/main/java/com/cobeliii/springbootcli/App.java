package com.cobeliii.springbootcli;

import com.cobeliii.springbootcli.car.CarRepository;
import com.cobeliii.springbootcli.car.CarServiceImpl;
import com.cobeliii.springbootcli.user.UserDto;
import com.cobeliii.springbootcli.user.UserRepository;
import com.cobeliii.springbootcli.user.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CarRepository carRepository,
                                               UserRepository userRepository,
                                               CarServiceImpl carServiceImpl,
                                               UserServiceImpl userServiceImpl) {
        return args -> {
            while (true) {
                menu();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        scanner.nextLine();
                        carServiceImpl.getAllAvailableCars().forEach(System.out::println);
                        System.out.println("Enter car id: ");
                        UUID carId = UUID.fromString(scanner.nextLine());
                        userServiceImpl.getAllUsers().forEach(System.out::println);
                        System.out.println("Enter user id:");
                        UUID userId = UUID.fromString(scanner.nextLine());
                        //bookingService.addBooking(carId, userId);
                        break;
                    case 2:
                        scanner.nextLine();
                        userServiceImpl.getAllUsers().forEach(System.out::println);
                        System.out.println("Enter user name: ");
                        String userName = scanner.nextLine();
                        Optional<UserDto> user = userServiceImpl.getUserByName(userName);
//                        bookingService.getAllUserBookedCars(user)
//                                .forEach(System.out::println);
                        break;
                    case 3:
//                        bookingService.getBookings()
//                                .forEach(System.out::println);
                        break;
                    case 4:
                        carServiceImpl.getAllAvailableCars()
                                .forEach(System.out::println);
                        break;
                    case 5:
                        System.out.println("Available Electric cars: ");
                        carServiceImpl.getAvailableElectricCars()
                                .forEach(System.out::println);
                        break;
                    case 6:
                        userServiceImpl.getAllUsers()
                                .forEach(System.out::println);
                        break;
                    case 7:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }

            }
        };
    }

    public static void menu(){
        System.out.println("1️⃣ - Book Car");
        System.out.println("2️⃣ - View All User Booked Cars");
        System.out.println("3️⃣ - View All Bookings");
        System.out.println("4️⃣ - View Available Cars");
        System.out.println("5️⃣ - View Available Electric Cars");
        System.out.println("6️⃣ - View All Users");
        System.out.println("7️⃣ - Exit");
    }
}
