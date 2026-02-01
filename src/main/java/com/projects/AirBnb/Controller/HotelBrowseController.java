package com.projects.AirBnb.Controller;

import com.projects.AirBnb.DTO.HotelDto;
import com.projects.AirBnb.DTO.HotelInfoDto;
import com.projects.AirBnb.DTO.HotelPriceDto;
import com.projects.AirBnb.DTO.HotelSearchRequest;
import com.projects.AirBnb.Service.HotelService;
import com.projects.AirBnb.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {
    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest)
    {
        Page<HotelPriceDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId)
    {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
