package com.projects.AirBnb.Repository;

import com.projects.AirBnb.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // we can ignore this also as we are extending JpaRepository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
