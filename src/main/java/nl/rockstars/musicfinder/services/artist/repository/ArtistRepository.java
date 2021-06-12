package nl.rockstars.musicfinder.services.artist.repository;

import nl.rockstars.musicfinder.services.artist.model.Artist;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArtistRepository extends PagingAndSortingRepository<Artist, String> {
    Artist findFirstById(int id);
    Artist findFirstByName(String name);
    List<Artist> findByNameContaining(String name);
}
