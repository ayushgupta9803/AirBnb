package com.projects.AirBnb.Repository;

import com.projects.AirBnb.Entity.Inventory;
import com.projects.AirBnb.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository // we can ignore this also as we are extending JpaRepository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    void deleteByDateAfterAndRoom(LocalDateTime date , Room room);
}
