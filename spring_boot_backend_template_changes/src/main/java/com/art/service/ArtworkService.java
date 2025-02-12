package com.art.service;

import java.util.List;

import com.art.dto.ArtworkDto;

public interface ArtworkService {
	 List<ArtworkDto> getAllArtworks();
	    String addArtwork(ArtworkDto artworkDTO);
	    String deleteArtwork(Long artId);
	    ArtworkDto getArtworkDetails(Long artId);
	    ArtworkDto updateArtwork(Long artId, ArtworkDto artworkDTO);
}
