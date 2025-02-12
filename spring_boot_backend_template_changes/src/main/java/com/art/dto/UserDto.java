package com.art.dto;

import java.util.Date;

import com.art.pojos.User.Role;

import lombok.Data;

@Data
public class UserDto {
	private Long userId;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
    private Role role; // ADMIN or CUSTOMER
    private Date dateCreated;
}
