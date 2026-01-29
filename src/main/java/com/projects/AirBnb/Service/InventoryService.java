package com.projects.AirBnb.Service;

import com.projects.AirBnb.Entity.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);
}
