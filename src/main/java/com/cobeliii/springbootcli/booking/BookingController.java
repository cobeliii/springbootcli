package com.cobeliii.springbootcli.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingServiceImpl bookingService;

    @Autowired
    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping()
    public List<BookingDto> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @PostMapping("/{car_id}/{user_id}")
    public Booking bookCar(@PathVariable("car_id") Long carId, @PathVariable("user_id") Long userId){
        return bookingService.bookCar(carId, userId);
    }

    @DeleteMapping("/{booking_id}")
    public void cancelBookingById(@PathVariable("booking_id") Long bookingId){
        bookingService.cancelBookingById(bookingId);
    }

    @GetMapping("/{user_id}")
    public List<BookingDto> getBookingByUserId(@PathVariable("user_id") Long userId){
        return bookingService.getBookingByUserId(userId);
    }
}
