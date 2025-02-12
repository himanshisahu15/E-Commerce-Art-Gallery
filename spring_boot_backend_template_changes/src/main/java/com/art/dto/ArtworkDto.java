package com.art.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkDto {
	    private Long artId;
	    private String title;
	   
	    private double price;
	    private boolean availability;
	    private String imageUrl;
	    private Long artistId; // FK
	    private Long categoryId; // FK
}
