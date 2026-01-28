package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId,RoomDto RoomDto);
    List<RoomDto> getAllRoomsInHotel(Long hotelId);
    RoomDto getRoomById(Long roomId);
    void DeleteRoomById(Long roomId);
}
