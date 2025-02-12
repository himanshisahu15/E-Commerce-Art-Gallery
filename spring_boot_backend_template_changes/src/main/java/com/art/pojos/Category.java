package com.art.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Category {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long categoryId;
	    private String categoryName;

//	    @OneToMany(mappedBy = "category")
//	    private List<Artwork> artworks;
}
