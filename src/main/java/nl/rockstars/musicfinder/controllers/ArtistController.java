package nl.rockstars.musicfinder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rockstars.musicfinder.services.artist.ArtistService;
import nl.rockstars.musicfinder.services.artist.model.Artist;
import nl.rockstars.musicfinder.controllers.model.ArtistDto;
import nl.rockstars.musicfinder.util.ModelMapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@RestController
public class ArtistController {
    private final ArtistService artistService;

    private final ModelMapper modelMapper;

    @Autowired
    public ArtistController(ArtistService artistService, ModelMapper modelMapper) {
        this.artistService = artistService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/artist/reset")
    @ResponseStatus(HttpStatus.CREATED)
    void reset(@RequestParam("file") MultipartFile artistsFile) {
        String artistsJson;
        ArtistDto[] artistDtos;

        try {
            artistsJson = StreamUtils.copyToString(artistsFile.getInputStream(), Charset.defaultCharset());

            ObjectMapper mapper = new ObjectMapper();
            artistDtos = mapper.readValue(artistsJson, ArtistDto[].class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Artist> artists = ModelMapperHelper.mapList(modelMapper, Arrays.asList(artistDtos), Artist.class);
        artistService.reset(artists);
    }

    @PostMapping("/api/artist")
    @ResponseStatus(HttpStatus.CREATED)
    int add(@Valid @RequestBody ArtistDto artistDto) {
        var artist = modelMapper.map(artistDto, Artist.class);
        if (!artistService.add(artist)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return artist.id;
    }

    @GetMapping("/api/artist")
    @ResponseStatus(HttpStatus.OK)
    List<ArtistDto> find(String name) {
        var artists = artistService.getByName(name);
        return ModelMapperHelper.mapList(modelMapper, artists, ArtistDto.class);
    }

    @PutMapping("/api/artist/{id}")
    @ResponseStatus(HttpStatus.OK)
    int update(@PathVariable int id, @Valid @RequestBody ArtistDto artistDto) {
        var artist = modelMapper.map(artistDto, Artist.class);

        if (!artistService.update(id, artist)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return id;
    }

    @DeleteMapping("/api/artist/{id}")
    @ResponseStatus(HttpStatus.OK)
    int remove(@PathVariable int id) {
        if (!artistService.remove(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return id;
    }
}
