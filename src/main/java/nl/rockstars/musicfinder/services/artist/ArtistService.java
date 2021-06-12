package nl.rockstars.musicfinder.services.artist;

import nl.rockstars.musicfinder.services.artist.model.Artist;

import java.util.List;

public interface ArtistService {
    void reset(List<Artist> artists);
    boolean add(Artist artist);
    List<Artist> getByName(String name);
    boolean update(int id, Artist artist);
    boolean remove(int id);
}
