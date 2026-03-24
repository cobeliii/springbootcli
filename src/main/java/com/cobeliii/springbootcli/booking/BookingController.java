package com.cobeliii.springbootcli.booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingDto> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @PostMapping
    public ResponseEntity<Booking> bookCar(@RequestBody BookingRequest newRequest){
        Booking booking = bookingService.bookCar(newRequest.carId(), newRequest.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @DeleteMapping("/{bookingId}")
    public void cancelBookingById(@PathVariable Long bookingId){
        bookingService.cancelBookingById(bookingId);
    }

}
