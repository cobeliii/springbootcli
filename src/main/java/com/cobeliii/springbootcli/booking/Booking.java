package com.cobeliii.springbootcli.booking;

import com.cobeliii.springbootcli.car.Car;
import com.cobeliii.springbootcli.user.User;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = false)
    private Car car;
    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;
    @Column(nullable = false)
    private boolean isActive;

    public Booking() {
    }

    public Booking(User user, Car car) {
        this.user = user;
        this.car = car;
        this.startTime = LocalDateTime.now();
        this.endTime = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car carId) {
        this.car = carId;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(user, booking.user) && Objects.equals(car, booking.car) && Objects.equals(startTime, booking.startTime) && Objects.equals(endTime, booking.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, car, startTime, endTime);
    }


}
