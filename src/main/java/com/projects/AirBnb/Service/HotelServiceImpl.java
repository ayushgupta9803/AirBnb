package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.HotelDto;
import com.projects.AirBnb.Entity.Hotel;
import com.projects.AirBnb.Entity.Room;
import com.projects.AirBnb.Repository.HotelRepository;
import com.projects.AirBnb.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository; // constructor injection is happening because of requiredArgsConstructor so that's we don't any autowired keyword
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}",hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}", hotelDto.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        Hotel hotel = hotelRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ id));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public void DeleteHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ hotelId));

        hotelRepository.deleteById(hotelId);

        for(Room room : hotel.getRooms()) {
            inventoryService.deleteFutureInventories(room);
        }
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ hotelId));
        hotel.setActive(true);

        // assuming only do it once

        for(Room room : hotel.getRooms())
        {
            inventoryService.initializeRoomForAYear(room);
        }
    }
}
