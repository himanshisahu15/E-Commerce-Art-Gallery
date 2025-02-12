package com.art.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.art.dao.ArtistDao;
import com.art.dao.ArtworkDao;
import com.art.dao.CategoryDao;
import com.art.dto.ArtworkDto;
import com.art.exception.ResourceNotFoundException;
import com.art.pojos.Artist;
import com.art.pojos.Artwork;
import com.art.pojos.Category;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArtworkServiceImpl implements ArtworkService {

    @Autowired
    private ArtworkDao artworkRepository;
    
    @Autowired
    private ArtistDao artistRepository;
    
    @Autowired
    private CategoryDao categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ArtworkDto>getAllArtworks() {
    	return artworkRepository.findAll().stream()
                .map(artwork -> {
                    ArtworkDto artworkDto = mapper.map(artwork, ArtworkDto.class);
                    // Set the artist ID and category ID in the DTO
                    artworkDto.setArtistId(artwork.getArtist().getArtistId());
                    artworkDto.setCategoryId(artwork.getCategory().getCategoryId());
                    return artworkDto;
                })
                .collect(Collectors.toList());
    	
    }
    
    @Override
    public String addArtwork(ArtworkDto artworkDTO) {
    	
    	   
  System.out.println(artworkDTO.getArtistId());
  Long artistId=artworkDTO.getArtistId();
  Long categoryId=artworkDTO.getCategoryId();
          
          
          // Fetch the artist and category from the database
          Artist artist = artistRepository.findById(artistId)
                  .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
          Category category = categoryRepository.findById(categoryId)
                  .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

       // Map the DTO to the entity
          Artwork artwork = mapper.map(artworkDTO, Artwork.class);
          // Set the fetched artist and category
          artwork.setArtist(artist);
          artwork.setCategory(category);

          // Save the artwork
          Artwork savedArtwork = artworkRepository.save(artwork);
          return "New Artwork added with id " + savedArtwork.getArtId();
    	
   
    }
  

    @Override
    public String deleteArtwork(Long artId) {
        if (artworkRepository.existsById(artId)) {
            artworkRepository.deleteById(artId);
            return "Artwork deleted";
        }
        throw new ResourceNotFoundException("Invalid Artwork ID!");
    }

    @Override
    public ArtworkDto getArtworkDetails(Long artId) {
        Artwork artwork = artworkRepository.findById(artId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Artwork ID"));
        Long artistid=artwork.getArtist().getArtistId();
        Long categoryid=artwork.getCategory().getCategoryId();
        ArtworkDto artDto=mapper.map(artwork, ArtworkDto.class);
        artDto.setArtistId(artistid);
        artDto.setCategoryId(categoryid);
        return artDto;
    }

    @Override
    public ArtworkDto updateArtwork(Long artId, ArtworkDto artworkDTO) {
    	Artwork artwork = artworkRepository.findById(artId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Artwork ID"));

        // Update the artwork entity with the new details
        artwork.setTitle(artworkDTO.getTitle());
        artwork.setPrice(artworkDTO.getPrice());
        artwork.setAvailability(artworkDTO.isAvailability());
        artwork.setImageUrl(artworkDTO.getImageUrl());

        // Update the artist and category if they have changed
        if (artworkDTO.getArtistId() != null) {
            Artist artist = artistRepository.findById(artworkDTO.getArtistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
            artwork.setArtist(artist);
        }
        if (artworkDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(artworkDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            artwork.setCategory(category);
        }

        // Save the updated artwork
        Artwork updatedArtwork = artworkRepository.save(artwork);

        // Map the updated artwork to the DTO
        ArtworkDto updatedArtworkDTO = mapper.map(updatedArtwork, ArtworkDto.class);
        updatedArtworkDTO.setArtistId(updatedArtwork.getArtist().getArtistId());
        updatedArtworkDTO.setCategoryId(updatedArtwork.getCategory().getCategoryId());

        return updatedArtworkDTO;
    }
}
