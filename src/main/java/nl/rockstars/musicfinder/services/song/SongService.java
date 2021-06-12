package nl.rockstars.musicfinder.services.song;

import nl.rockstars.musicfinder.services.song.model.Song;

import java.util.List;

public interface SongService {
    void reset(List<Song> songs);
    boolean add(Song song);
    List<Song> getByGenre(String genre);
    boolean update(int id, Song song);
    boolean remove(int id);
}
