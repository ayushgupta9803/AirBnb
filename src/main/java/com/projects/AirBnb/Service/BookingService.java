package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.BookingDto;
import com.projects.AirBnb.DTO.BookingRequest;
import com.projects.AirBnb.DTO.GuestDto;
import com.stripe.model.Event;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    String initiatePayment(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);
}
