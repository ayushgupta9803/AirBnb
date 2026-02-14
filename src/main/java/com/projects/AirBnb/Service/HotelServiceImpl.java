package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.HotelDto;
import com.projects.AirBnb.DTO.HotelInfoDto;
import com.projects.AirBnb.DTO.RoomDto;
import com.projects.AirBnb.Entity.Hotel;
import com.projects.AirBnb.Entity.Room;
import com.projects.AirBnb.Entity.User;
import com.projects.AirBnb.Repository.HotelRepository;
import com.projects.AirBnb.Repository.RoomRepository;
import com.projects.AirBnb.exception.ResourceNotFoundException;
import com.projects.AirBnb.exception.UnAuthorisedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final RoomRepository roomRepository;

    private final HotelRepository hotelRepository; // constructor injection is happening because of requiredArgsConstructor so that's we don't any autowired keyword
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}",hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);

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

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("this user doesn't own this hotel");
        }

        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("this user doesn't own this hotel");
        }

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

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("this user doesn't own this hotel");
        }

        for(Room room : hotel.getRooms()) {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(hotelId);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("this user doesn't own this hotel");
        }

        hotel.setActive(true);
        //hotelRepository.save(hotel);
        hotelRepository.saveAndFlush(hotel);
        // assuming only do it once

        for(Room room : hotel.getRooms())
        {
            inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        log.info("finding hotel and all the room info with ID: {}", hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ hotelId));

        List<RoomDto> rooms = hotel.getRooms().stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());


        return new HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }
}
