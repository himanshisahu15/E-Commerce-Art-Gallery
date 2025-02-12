package com.art.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ShippingDto {
	 private Long id;
	   
	    private Date date;
	    private String address;
	    private String status;
}
