package nl.rockstars.musicfinder.controllers;

import nl.rockstars.musicfinder.controllers.model.ArtistDto;
import nl.rockstars.musicfinder.services.artist.ArtistService;
import nl.rockstars.musicfinder.services.artist.model.Artist;
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
public class ArtistControllerTest {
    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private ArtistService mockArtistService;

    private ArtistController artistController;

    @Before
    public void init() {
        artistController = new ArtistController(mockArtistService, modelMapper);
    }

    @Test
    public void reset_wrongFile_throwsException() throws Exception {
        var artists = Files.readAllBytes(Paths.get("src/test/resources/wrong-artists.json"));
        MockMultipartFile artistsFile = new MockMultipartFile("artists", "artists", "application/json", artists);

        Assert.assertThrows(ResponseStatusException.class, () -> artistController.reset(artistsFile));
    }

    @Test
    public void reset_correctFile_doesNotThrowException() throws Exception {
        var artists = Files.readAllBytes(Paths.get("src/test/resources/correct-artists.json"));
        MockMultipartFile artistsFile = new MockMultipartFile("artists", "artists", "application/json", artists);

        artistController.reset(artistsFile);
    }

    @Test
    public void add_incorrect_throwsException() {
        var artist = new ArtistDto() {
            {
                id = 1;
                name = "name";
            }
        };

        Mockito.when(mockArtistService.add(Mockito.any())).thenReturn(false);

        Assert.assertThrows(ResponseStatusException.class, () -> artistController.add(artist));
    }

    @Test
    public void add_correct_returnsId() {
        var artist = new ArtistDto() {
            {
                id = 1;
                name = "name";
            }
        };

        Mockito.when(mockArtistService.add(Mockito.any())).thenReturn(true);

        var artistId = artistController.add(artist);

        Assert.assertEquals(1, artistId);
    }

    @Test
    public void find_correct_returnsList() {
        var name = "nam";
        var artists = Arrays.asList(new Artist() {
            {
                id = 1;
                name = "name";
            }
        }, new Artist() {
            {
                id = 2;
                name = "names";
            }
        });

        Mockito.when(mockArtistService.getByName("nam")).thenReturn(artists);

        var foundArtists = artistController.find(name);

        Assert.assertEquals(2, foundArtists.size());
    }

    @Test
    public void update_incorrect_throwsException() {
        var artist = new ArtistDto() {
            {
                name = "name";
            }
        };

        Mockito.when(mockArtistService.update(Mockito.anyInt(), Mockito.any())).thenReturn(false);

        Assert.assertThrows(ResponseStatusException.class, () -> artistController.update(1, artist));
    }

    @Test
    public void update_correct_ReturnsId() {
        var artist = new ArtistDto() {
            {
                name = "name";
            }
        };

        Mockito.when(mockArtistService.update(Mockito.anyInt(), Mockito.any())).thenReturn(true);

        var artistId = artistController.update(1, artist);

        Assert.assertEquals(1, artistId);
    }

    @Test
    public void remove_incorrect_throwsException() {
        Mockito.when(mockArtistService.remove(Mockito.anyInt())).thenReturn(false);

        Assert.assertThrows(ResponseStatusException.class, () -> artistController.remove(1));
    }

    @Test
    public void remove_correct_ReturnsId() {
        Mockito.when(mockArtistService.remove(Mockito.anyInt())).thenReturn(true);

        var artistId = artistController.remove(1);

        Assert.assertEquals(1, artistId);
    }
}
