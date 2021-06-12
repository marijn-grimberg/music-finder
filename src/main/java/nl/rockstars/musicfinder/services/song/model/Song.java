package nl.rockstars.musicfinder.services.song.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Song {
    @Id
    public int id;

    public String name;

    public int year;

    public String artist;

    public String shortname;

    public int bpm;

    public int duration;

    public String genre;

    public String spotifyId;

    public String album;
}
