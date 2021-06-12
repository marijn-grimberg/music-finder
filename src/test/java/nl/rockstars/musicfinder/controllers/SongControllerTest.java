package nl.rockstars.musicfinder.controllers;

import nl.rockstars.musicfinder.controllers.model.SongDto;
import nl.rockstars.musicfinder.services.song.SongService;
import nl.rockstars.musicfinder.services.song.model.Song;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SongControllerTest {
    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private SongService mockSongService;

    private SongController songController;

    @Before
    public void init() {
        songController = new SongController(mockSongService, modelMapper);
    }

    @Test
    public void reset_wrongFile_returnsBadResponse() throws Exception {
        var songs = Files.readAllBytes(Paths.get("src/test/resources/wrong-songs.json"));
        MockMultipartFile songsFile = new MockMultipartFile("songs", "songs", "application/json", songs);

        Assert.assertThrows(ResponseStatusException.class, () -> songController.reset(songsFile));
    }

    @Test
    public void reset_correctFile_returnsCreatedResponse() throws Exception {
        var songs = Files.readAllBytes(Paths.get("src/test/resources/correct-songs.json"));
        MockMultipartFile songsFile = new MockMultipartFile("songs", "songs", "application/json", songs);

        songController.reset(songsFile);
    }

    @Test
    public void add_incorrect_throwsException() {
        var song = new SongDto() {
            {
                id = 1;
                name = "name";
            }
        };

        Mockito.when(mockSongService.add(Mockito.any())).thenReturn(false);

        Assert.assertThrows(ResponseStatusException.class, () -> songController.add(song));
    }

    @Test
    public void add_correct_returnsId() {
        var song = new SongDto() {
            {
                id = 1;
                name = "name";
            }
        };

        Mockito.when(mockSongService.add(Mockito.any())).thenReturn(true);

        var songId = songController.add(song);

        Assert.assertEquals(1, songId);
    }

    @Test
    public void find_correct_returnsList() {
        var name = "gen";
        var songs = Arrays.asList(new Song() {
            {
                id = 1;
                name = "genr";
            }
        }, new Song() {
            {
                id = 2;
                name = "genre";
            }
        });

        Mockito.when(mockSongService.getByGenre("gen")).thenReturn(songs);

        var foundSongs = songController.find(name);

        Assert.assertEquals(2, foundSongs.size());
    }

    @Test
    public void update_incorrect_throwsException() {
        var song = new SongDto() {
            {
                name = "name";
            }
        };

        Mockito.when(mockSongService.update(Mockito.anyInt(), Mockito.any())).thenReturn(false);

        Assert.assertThrows(ResponseStatusException.class, () -> songController.update(1, song));
    }

    @Test
    public void update_correct_ReturnsId() {
        var song = new SongDto() {
            {
                name = "name";
            }
        };

        Mockito.when(mockSongService.update(Mockito.anyInt(), Mockito.any())).thenReturn(true);

        var songId = songController.update(1, song);

        Assert.assertEquals(1, songId);
    }

    @Test
    public void remove_incorrect_throwsException() {
        Mockito.when(mockSongService.remove(Mockito.anyInt())).thenReturn(false);

        Assert.assertThrows(ResponseStatusException.class, () -> songController.remove(1));
    }

    @Test
    public void remove_correct_ReturnsId() {
        Mockito.when(mockSongService.remove(Mockito.anyInt())).thenReturn(true);

        var songId = songController.remove(1);

        Assert.assertEquals(1, songId);
    }
}
