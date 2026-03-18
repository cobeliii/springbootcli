package com.cobeliii.springbootcli.booking;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    Booking bookCar(Long carId, Long userId);
    void cancelBookingById(Long bookingId);
    List<BookingDto> getAllBookings();
}
