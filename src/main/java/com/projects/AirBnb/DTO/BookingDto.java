package com.projects.AirBnb.DTO;

import com.projects.AirBnb.Entity.Enums.BookingStatus;
import com.projects.AirBnb.Entity.Guest;
import com.projects.AirBnb.Entity.Hotel;
import com.projects.AirBnb.Entity.Room;
import com.projects.AirBnb.Entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDate updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
