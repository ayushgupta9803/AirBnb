package com.projects.AirBnb.Controller;

import com.projects.AirBnb.DTO.BookingDto;
import com.projects.AirBnb.DTO.BookingRequest;
import com.projects.AirBnb.DTO.GuestDto;
import com.projects.AirBnb.Service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest)
    {
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
            @RequestBody List<GuestDto> guestDtoList)
    {
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }

    @PostMapping("/{bookingId}/payments")
    public ResponseEntity<Map<String,String>> initiatePayment(@PathVariable Long bookingId)
    {
        String sessionUrl = bookingService.initiatePayment(bookingId);
        return ResponseEntity.ok(Map.of("sessionUrl" , sessionUrl));
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Map<String,String>> cancelBooking(@PathVariable Long bookingId)
    {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
