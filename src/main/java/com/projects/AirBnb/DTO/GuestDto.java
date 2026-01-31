package com.projects.AirBnb.DTO;

import com.projects.AirBnb.Entity.Enums.Gender;
import com.projects.AirBnb.Entity.User;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
