import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for redirection
import './ArtistsPage.css'; // Import CSS for styling

const ArtistsPage = () => {
    const [artists, setArtists] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate(); // Initialize useNavigate

    useEffect(() => {
        const fetchArtists = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/artists');
                setArtists(response.data);
            } catch (err) {
                console.error(err);
                setError('Failed to fetch artists. Please try again later.');
            }
        };

        fetchArtists();
    }, []);

    const handleArtistClick = (artistId) => {
        // Redirect to the category page for the selected artist
        navigate(`/category/${artistId}`);
    };

    return (
        <div className="artists-container">
            <h2>Artists</h2>
            {error && <p className="error-message">{error}</p>}
            <div className="artists-list">
                {artists.length > 0 ? (
                    artists.map((artist) => (
                        <div key={artist.artistId} className="artist-card" onClick={() => handleArtistClick(artist.artistId)}>
                            <h3 className="artist-name">{artist.name}</h3>
                            <p className="artist-biography">{artist.biography}</p>
                            <p className="artist-country">Country: {artist.country}</p>
                            <p className="artist-contact">Contact: {artist.contactNo}</p>
                            <p className="artist-email">Email: {artist.email}</p>
                        </div>
                    ))
                ) : (
                    <p>No artists found.</p>
                )}
            </div>
        </div>
    );
};

export default ArtistsPage;