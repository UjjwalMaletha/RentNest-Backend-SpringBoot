package com.ujjwalmaletha.arbnbbackend.booking.repository;

import com.ujjwalmaletha.arbnbbackend.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
