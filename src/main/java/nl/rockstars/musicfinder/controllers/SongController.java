package nl.rockstars.musicfinder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rockstars.musicfinder.controllers.model.SongDto;
import nl.rockstars.musicfinder.services.song.SongService;
import nl.rockstars.musicfinder.services.song.model.Song;
import nl.rockstars.musicfinder.util.ModelMapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@RestController
@Validated
public class SongController {
    private final SongService songService;

    private final ModelMapper modelMapper;

    @Autowired
    public SongController(SongService songService, ModelMapper modelMapper) {
        this.songService = songService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/song/reset")
    @ResponseStatus(HttpStatus.CREATED)
    void reset(@RequestParam("file") MultipartFile songsFile) {
        String songsJson;
        SongDto[] songDtos;

        try {
            songsJson = StreamUtils.copyToString(songsFile.getInputStream(), Charset.defaultCharset());

            ObjectMapper mapper = new ObjectMapper();
            songDtos = mapper.readValue(songsJson, SongDto[].class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Song> songs = ModelMapperHelper.mapList(modelMapper, Arrays.asList(songDtos), Song.class);
        songService.reset(songs);
    }

    @PostMapping("/api/song")
    @ResponseStatus(HttpStatus.CREATED)
    int add(@Valid @RequestBody SongDto songDto) {
        var song = modelMapper.map(songDto, Song.class);
        if (!songService.add(song)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return song.id;
    }

    @GetMapping("/api/song")
    @ResponseStatus(HttpStatus.OK)
    List<SongDto> find(String genre) {
        var songs = songService.getByGenre(genre);
        return ModelMapperHelper.mapList(modelMapper, songs, SongDto.class);
    }

    @PutMapping("/api/song/{id}")
    @ResponseStatus(HttpStatus.OK)
    int update(@PathVariable int id, @Valid @RequestBody SongDto songDto) {
        var song = modelMapper.map(songDto, Song.class);

        if (!songService.update(id, song)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return id;
    }

    @DeleteMapping("/api/song/{id}")
    @ResponseStatus(HttpStatus.OK)
    int remove(@PathVariable int id) {
        if (!songService.remove(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return id;
    }
}
