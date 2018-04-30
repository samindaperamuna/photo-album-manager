package edu.csuohio.photomanager.rest;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import edu.csuohio.photomanager.data.service.StorageProperties;
import edu.csuohio.photomanager.data.service.StorageService;
import edu.csuohio.photomanager.util.ImageUtil;

@RestController
public class DefaultController {

	private final StorageService storageService;

	@Autowired
	StorageProperties properties;

	public DefaultController(StorageService storageService) {
		this.storageService = storageService;
	}

	public ResponseEntity<?> getAll() {
		return null;
	}

	@GetMapping("/images/{filename:.+}")
	public ResponseEntity<Resource> serveImages(@PathVariable String filename) throws MalformedURLException {
		Path file = Paths.get(properties.getLocation() + "/" + filename);
		Resource resource = new UrlResource(file.toUri());

		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
	}

	@GetMapping("/thumbs/{filename:.+}")
	public ResponseEntity<Resource> serveThumbs(@PathVariable String filename) throws MalformedURLException {
		Path file = Paths.get(properties.getLocation() + ImageUtil.THUMBNAIL_PATH + filename);
		Resource resource = new UrlResource(file.toUri());

		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
	}

	@GetMapping("/thumbnails")
	public ResponseEntity<?> getThumbnails() {
		List<String> images = storageService.loadThumbnails().map(path -> {
			return MvcUriComponentsBuilder
					.fromMethodName(DefaultController.class, "serveThumbs", path.getFileName().toString()).build()
					.toString();
		}).collect(Collectors.toList());

		return ResponseEntity.ok().body(images);
	}
}
