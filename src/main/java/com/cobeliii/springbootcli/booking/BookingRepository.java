package com.cobeliii.springbootcli.booking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    int countByUserIdAndIsActiveTrue(Long userId);

    boolean existsByUserIdAndCarIdAndIsActiveTrue(Long userId, Long carId);
}
