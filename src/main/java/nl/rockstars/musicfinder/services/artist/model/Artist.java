package nl.rockstars.musicfinder.services.artist.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Artist {
    @Id
    public int id;

    public String name;
}
