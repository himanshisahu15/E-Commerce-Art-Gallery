package com.art.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Artwork {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long artId;
	  
	    private String title;
	    
	    private double price;
	    
	    private boolean availability;
	    
	    private String imageUrl;
	    
	    @ManyToOne(fetch=FetchType.LAZY,optional = false)
	    @JoinColumn(name = "artist_id",nullable=false)
	    private Artist artist;
	  
	   
	    
	    @ManyToOne(fetch=FetchType.LAZY,optional = false)
	    @JoinColumn(name = "category_id",nullable=false)
	    private Category category;
}
