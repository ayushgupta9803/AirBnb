package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.RoomDto;
import com.projects.AirBnb.Entity.Hotel;
import com.projects.AirBnb.Entity.Room;
import com.projects.AirBnb.Repository.HotelRepository;
import com.projects.AirBnb.Repository.RoomRepository;
import com.projects.AirBnb.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Override
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {
        log.info("Creating a new Room in hotel with Id: {}",hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ hotelId));
        Room room = modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
        room = RoomRepository.save(room);

        // TODO create the inventory for the room as soon as it is created and hotel is active

        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all the Rooms in hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository.
                findById(hotelId).
                orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID "+ hotelId));

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

//    @Override
//    public RoomDto updateRoomById(Long id, RoomDto RoomDto) {
//        log.info("Updating the Room with ID: {}", id);
//        Room Room = RoomRepository.
//                findById(id).
//                orElseThrow(() -> new ResourceNotFoundException("Room not found with ID "+ id));
//        modelMapper.map(RoomDto,Room);
//        Room.setId(id);
//        RoomRepository.save(Room);
//        return modelMapper.map(Room,RoomDto.class);
//    }

    @Override
    public void DeleteRoomById(Long id) {
        log.info("Deleting the Room with ID: {}", id);
        boolean exists = RoomRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Room not found with ID "+ id);

        RoomRepository.deleteById(id);
        // TODO: delete the future inventories for this Room
    }
}
