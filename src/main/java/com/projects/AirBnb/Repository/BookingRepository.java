package com.projects.AirBnb.Repository;

import com.projects.AirBnb.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
