package com.cobeliii.springbootcli.booking;


import java.util.List;

public interface BookingService {
    BookingDto bookCar(Long carId, Long userId);
    void cancelBookingById(Long bookingId);
    List<BookingDto> getAllBookings();
}
