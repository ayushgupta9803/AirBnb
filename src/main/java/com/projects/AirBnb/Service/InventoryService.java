package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.HotelDto;
import com.projects.AirBnb.DTO.HotelPriceDto;
import com.projects.AirBnb.DTO.HotelSearchRequest;
import com.projects.AirBnb.Entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
