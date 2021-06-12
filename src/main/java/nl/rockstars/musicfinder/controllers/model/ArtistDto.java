package nl.rockstars.musicfinder.controllers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArtistDto {
    @JsonProperty("Id")
    public int id;

    @JsonProperty("Name")
    public String name;
}
