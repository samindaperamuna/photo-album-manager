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

	/**
	 * Main entry point to the Spring Boot application.
	 * 
	 * @param args Command line arguments if any.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PhotoAlbumManagerApplication.class, args);
	}

	/**
	 * This bean is used to initialise the storage service on every startup. Comment
	 * if you don't want the data to be deleted everytime you restart the
	 * application.
	 * 
	 * @param storageService
	 * @return
	 */
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}