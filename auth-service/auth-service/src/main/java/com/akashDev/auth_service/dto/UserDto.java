package com.akashDev.auth_service.dto;

import lombok.Data;

@Data
public class UserDto {
    private String Fname;
    private String Lname;
    private String dob;
    private String email;
    private String password;
}
