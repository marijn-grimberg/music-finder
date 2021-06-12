package nl.rockstars.musicfinder.services.song;

import nl.rockstars.musicfinder.services.song.model.Song;
import nl.rockstars.musicfinder.services.song.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {
    private final MongoTemplate mongoTemplate;
    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(MongoTemplate mongoTemplate, SongRepository songRepository) {
        this.mongoTemplate = mongoTemplate;
        this.songRepository = songRepository;
    }

    public void reset(List<Song> songs) {
        mongoTemplate.dropCollection(Song.class);

        var filteredSongs = songs.stream().filter(s -> s.genre != null && s.genre.contains("Metal") || s.year < 2016).collect(Collectors.toList());
        mongoTemplate.insert(filteredSongs, Song.class);
    }

    public boolean add(Song song) {
        if(songRepository.findFirstById(song.id) != null || songRepository.findFirstByName(song.name) != null) {
            return false;
        }

        songRepository.save(song);

        return true;
    }

    public List<Song> getByGenre(String genre) {
        return songRepository.findByGenreContaining(genre);
    }

    public boolean update(int id, Song song) {
        var songEntity = songRepository.findFirstById(id);

        if(songEntity == null) {
            return false;
        }

        song.id = id;
        songRepository.save(song);

        return true;
    }

    public boolean remove(int id) {
        var song = songRepository.findFirstById(id);

        if (song == null) {
            return false;
        }

        songRepository.delete(song);

        return true;
    }
}
