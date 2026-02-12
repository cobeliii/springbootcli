package com.cobeliii.springbootcli.booking;

import com.cobeliii.springbootcli.car.Car;
import com.cobeliii.springbootcli.user.Users;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
public class Booking {
    @Id
    @SequenceGenerator(name = "booking_seq",
            sequenceName = "booking_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE
            ,generator = "booking_seq"
    )
    private Long id;
    @ManyToOne
    private Users userId;
    @ManyToOne
    private Car carId;
    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;
    @Column(nullable = false)
    private boolean isActive;

    public Booking() {
    }

    public Booking(Users userId, Car carId) {
        this.userId = userId;
        this.carId = carId;
        this.startTime = LocalDateTime.now();
        this.endTime = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Car getCarId() {
        return carId;
    }

    public void setCarId(Car carId) {
        this.carId = carId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(userId, booking.userId) && Objects.equals(carId, booking.carId) && Objects.equals(startTime, booking.startTime) && Objects.equals(endTime, booking.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, carId, startTime, endTime);
    }


}
