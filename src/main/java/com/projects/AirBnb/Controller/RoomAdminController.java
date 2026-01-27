package com.projects.AirBnb.Controller;

import com.projects.AirBnb.DTO.RoomDto;
import com.projects.AirBnb.Service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomAdminController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId , @RequestBody RoomDto roomDto)
    {
        log.info("Creating a new room with Id {}" , roomDto.getId());
        RoomDto room = roomService.createNewRoom(hotelId,roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId)
    {
        List<RoomDto> roomDtoList = roomService.getAllRoomsInHotel(hotelId);
        return ResponseEntity.ok(roomDtoList);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long hotelId , @PathVariable Long roomId)
    {
        RoomDto room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long hotelId , @PathVariable Long roomId)
    {
        roomService.DeleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("/{roomId}")
//    public ResponseEntity<Void> activateRoom(@PathVariable Long roomId)
//    {
//        roomService.activateRoom(roomId);
//        return ResponseEntity.noContent().build();
//    }
}
