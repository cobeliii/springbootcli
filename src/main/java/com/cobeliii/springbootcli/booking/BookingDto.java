package com.cobeliii.springbootcli.booking;

import java.time.LocalDateTime;

public record BookingDto(
        Long id,
        Long userId,
        Long carId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean active
) {
}
