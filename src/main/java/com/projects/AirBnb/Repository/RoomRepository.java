package com.projects.AirBnb.Repository;

import com.projects.AirBnb.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // we can ignore this also as we are extending JpaRepository
public interface RoomRepository extends JpaRepository<Room,Long> {
}
