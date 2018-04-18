package edu.csuohio.photomanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import edu.csuohio.photomanager.data.service.StorageProperties;
import edu.csuohio.photomanager.data.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PhotoAlbumManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAlbumManagerApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}