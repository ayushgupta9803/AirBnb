package com.projects.AirBnb.Repository;

import com.projects.AirBnb.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}