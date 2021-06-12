package nl.rockstars.musicfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MusicFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicFinderApplication.class, args);
	}

}
