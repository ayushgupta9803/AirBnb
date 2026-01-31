package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.BookingDto;
import com.projects.AirBnb.DTO.BookingRequest;
import com.projects.AirBnb.DTO.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuest(Long bookingId, List<GuestDto> guestDtoList);
}
