package nl.rockstars.musicfinder.services.artist;

import nl.rockstars.musicfinder.services.artist.model.Artist;
import nl.rockstars.musicfinder.services.artist.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final MongoTemplate mongoTemplate;
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(MongoTemplate mongoTemplate, ArtistRepository artistRepository) {
        this.mongoTemplate = mongoTemplate;
        this.artistRepository = artistRepository;
    }

    public void reset(List<Artist> artists) {
        mongoTemplate.dropCollection(Artist.class);
        mongoTemplate.insert(artists, Artist.class);
    }

    public boolean add(Artist artist) {
        if(artistRepository.findFirstById(artist.id) != null || artistRepository.findFirstByName(artist.name) != null) {
            return false;
        }

        artistRepository.save(artist);

        return true;
    }

    public List<Artist> getByName(String name) {
        return artistRepository.findByNameContaining(name);
    }

    public boolean update(int id, Artist artist) {
        var artistEntity = artistRepository.findFirstById(id);

        if(artistEntity == null) {
            return false;
        }

        artist.id = id;
        artistRepository.save(artist);

        return true;
    }

    public boolean remove(int id) {
        var artist = artistRepository.findFirstById(id);

        if (artist == null) {
            return false;
        }

        artistRepository.delete(artist);

        return true;
    }
}
