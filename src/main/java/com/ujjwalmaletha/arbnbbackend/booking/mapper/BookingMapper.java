package com.ujjwalmaletha.arbnbbackend.booking.mapper;

import com.ujjwalmaletha.arbnbbackend.booking.application.dto.BookedDateDTO;
import com.ujjwalmaletha.arbnbbackend.booking.application.dto.NewBookingDTO;
import com.ujjwalmaletha.arbnbbackend.booking.domain.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking newBookingToBooking(NewBookingDTO newBookingDTO);

    BookedDateDTO bookingToCheckAvailability(Booking booking);
}
