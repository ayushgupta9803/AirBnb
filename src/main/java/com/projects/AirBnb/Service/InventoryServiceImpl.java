package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.HotelDto;
import com.projects.AirBnb.DTO.HotelPriceDto;
import com.projects.AirBnb.DTO.HotelSearchRequest;
import com.projects.AirBnb.Entity.Hotel;
import com.projects.AirBnb.Entity.Inventory;
import com.projects.AirBnb.Repository.HotelMinPriceRepository;
import com.projects.AirBnb.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.projects.AirBnb.Entity.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final HotelMinPriceRepository hotelMinPriceRepository;

    @Override
    public void initializeRoomForAYear(Room room) {
        log.info("initializing room for a year inventories with room id {}" , room.getId());
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        for(;!today.isAfter(endDate);today = today.plusDays(1))
        {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .date(today)
                    .totalCount(room.getTotalCount())
                    .surgeFactor(BigDecimal.ONE)
                    .price(room.getBasePrice())
                    .city(room.getHotel().getCity())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        log.info("deleting room with a year inventories with room id {} " , room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("Searching hotels for {} city , from {} to {} " ,
                hotelSearchRequest.getCity() , hotelSearchRequest.getStartDate() ,
                hotelSearchRequest.getEndDate());

        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());

        long dateCount = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),
                hotelSearchRequest.getEndDate()) + 1;

        // business logic - 90 days

        Page<HotelPriceDto> hotelPage = hotelMinPriceRepository.findHotelsWithAvailableInventory(
                hotelSearchRequest.getCity(),
                hotelSearchRequest.getStartDate(),
                hotelSearchRequest.getEndDate(),
                hotelSearchRequest.getRoomsCount(),
                dateCount , pageable);

        return hotelPage;
    }
}
