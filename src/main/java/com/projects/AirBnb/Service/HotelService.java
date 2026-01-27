package com.projects.AirBnb.Service;

import com.projects.AirBnb.DTO.HotelDto;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long Id);
    HotelDto updateHotelById(Long id,HotelDto hotelDto);
    void DeleteHotelById(Long id);
    void activateHotel(Long hotelId);
}
