package nl.rockstars.musicfinder.controllers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nl.rockstars.musicfinder.controllers.validators.Genre;
import nl.rockstars.musicfinder.controllers.validators.Year;

@Data
public class SongDto {
    @JsonProperty("Id")
    public int id;

    @JsonProperty("Name")
    public String name;

    @JsonProperty("Year")
    @Year
    public int year;

    @JsonProperty("Artist")
    public String artist;

    @JsonProperty("Shortname")
    public String shortname;

    @JsonProperty("Bpm")
    public int bpm;

    @JsonProperty("Duration")
    public int duration;

    @JsonProperty("Genre")
    @Genre
    public String genre;

    @JsonProperty("SpotifyId")
    public String spotifyId;

    @JsonProperty("Album")
    public String album;
}
