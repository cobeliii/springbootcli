package com.cobeliii.springbootcli.user;


import com.cobeliii.springbootcli.booking.Booking;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_seq"
    )
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;


    public User() {
        bookings = new ArrayList<>();
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        if (booking == null) return;
        if (bookings == null) bookings = new ArrayList<>();

        if (!bookings.contains(booking)) {
            bookings.add(booking);
        }
        if (booking.getUser() != this) {
            booking.setUser(this);
        }
    }

    public void removeBooking(Booking booking) {
        if (booking == null || bookings == null) return;

        bookings.remove(booking);
        if (booking.getUser() == this) {
            booking.setUser(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
