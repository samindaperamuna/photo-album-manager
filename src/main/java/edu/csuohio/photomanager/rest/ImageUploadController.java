package edu.csuohio.photomanager.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.csuohio.photomanager.data.service.StorageService;

@RestController
public class ImageUploadController {

	private static final String SUCCESS_MESSAGE = "Image saved successfuly.";
	private final StorageService storageService;

	public ImageUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@Autowired
	HttpServletRequest request;

	@PostMapping("/api/upload")
	public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
		storageService.store(file);

		return new ResponseEntity<>(SUCCESS_MESSAGE, HttpStatus.OK);
	}
}
