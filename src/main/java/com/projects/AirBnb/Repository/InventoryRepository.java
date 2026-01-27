package com.projects.AirBnb.Repository;

import com.projects.AirBnb.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // we can ignore this also as we are extending JpaRepository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
