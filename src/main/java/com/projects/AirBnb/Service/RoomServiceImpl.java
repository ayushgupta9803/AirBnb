package com.projects.AirBnb.Service;

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
public class RoomServiceImpl implements RoomService {

    private final RoomRepository RoomRepository; // constructor injection is happening because of requiredArgsConstructor so that's we don't any autowired keyword
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {
        log.info("Creating a new Room in hotel with Id: {}",hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("this user doesn't own this hotel");
        }

        Room room = modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
        room = RoomRepository.save(room);

        if(hotel.getActive())
        {
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all the Rooms in hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ hotelId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner()))
        {
            throw new UnAuthorisedException("this user doesn't own this hotel");
        }

        return hotel.getRooms().stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long id) {
        log.info("Getting the Room with ID: {}", id);
        Room room = RoomRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Room not found with ID "+ id));
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    @Transactional
    public void DeleteRoomById(Long id) {
        log.info("Deleting the Room with ID: {}", id);
        Room room = RoomRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Room not found with ID "+ id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(room.getHotel().getOwner()))
        {
            throw new UnAuthorisedException("this user doesn't own this hotel");
        }

        inventoryService.deleteAllInventories(room);
        RoomRepository.delete(room);
        RoomRepository.flush();
    }
}
