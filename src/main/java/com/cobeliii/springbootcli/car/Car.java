package com.cobeliii.springbootcli.car;

import com.cobeliii.springbootcli.booking.Booking;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Car {
    @Id
    @SequenceGenerator(
            name = "car_seq",
            sequenceName = "car_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_seq"
    )
    private Long id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String engineType;
    @Column(nullable = false)
    private boolean available;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Booking> bookings;
    public Car() {
        bookings = new ArrayList<>();
    }

    public Car(String brand, String model, String engineType) {
        this.brand = brand;
        this.model = model;
        if (!engineType.equalsIgnoreCase("electric") && !engineType.equalsIgnoreCase("petrol")){
            throw new IllegalArgumentException("Invalid engine type");
        }
        this.engineType = engineType;
        this.available = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        if (booking == null) return;

        if (!bookings.contains(booking)) {
            bookings.add(booking);
        }
        if (booking.getCar() != this) {
            booking.setCar(this);
        }
    }

    public void removeBooking(Booking booking) {
        if (booking == null || bookings == null) return;

        bookings.remove(booking);
        if (booking.getCar() == this) {
            booking.setUser(null);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return available == car.available && Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(engineType, car.engineType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, engineType, available);
    }
}
