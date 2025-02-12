package com.art.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDto {
	    private Long artistId;
	    private String name;
	    private String biography;
	    private String country;
	    private String contactNo;
	    private String email;
}
