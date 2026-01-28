package com.projects.AirBnb.Service;

import com.projects.AirBnb.Entity.Inventory;
import com.projects.AirBnb.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.projects.AirBnb.Entity.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        for(;!today.isAfter(endDate);today = today.plusDays(1))
        {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .date(today.atStartOfDay())
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
    public void deleteFutureInventories(Room room) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        inventoryRepository.deleteByDateAfterAndRoom(today, room);
    }
}
