package nl.rockstars.musicfinder.services.song.repository;

import nl.rockstars.musicfinder.services.song.model.Song;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SongRepository extends PagingAndSortingRepository<Song, String> {
    Song findFirstById(int id);
    Song findFirstByName(String name);
    List<Song> findByGenreContaining(String genre);
}
