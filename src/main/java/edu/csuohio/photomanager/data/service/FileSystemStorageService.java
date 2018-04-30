package edu.csuohio.photomanager.data.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.csuohio.photomanager.data.ImageItem;
import edu.csuohio.photomanager.data.exception.StorageException;
import edu.csuohio.photomanager.data.exception.StorageFileNotFoundException;
import edu.csuohio.photomanager.util.ImageUtil;

@Service
public class FileSystemStorageService implements StorageService {

	@Autowired
	ImageItemService imageItemService;

	public static final String IMG_PRE = "img-";

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void store(MultipartFile file) {
		// Get the original file name.
		String filename = StringUtils.cleanPath(file.getOriginalFilename());

		// Save an entry for the current image in the database.
		ImageItem imageItem = new ImageItem(filename, "");
		imageItem = imageItemService.save(imageItem);

		// Get the id from the saved <code>ImageItem</code> to deduce a new image name
		// based on the format "img-<id>".
		String imageName = IMG_PRE + imageItem.getId();

		// Update the new name on the entity and save again.
		imageItem.setImageName(imageName);
		imageItemService.save(imageItem);

		try {
			// Check if the file is empty.
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}

			// Save the image in the file system with the new name.
			try (InputStream inputStream = file.getInputStream()) {
				// Resolve path and save image.
				Path imagePath = rootLocation.resolve(imageName);
				Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);

				// Generate the thumbnail.
				ImageUtil.saveThumbnail(imagePath, imageItem.getId());
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation)).map(rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

	@Override
	public Path load(String fileName) {
		return rootLocation.resolve(fileName);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public Stream<Path> loadThumbnails() {
		Path thumbPath = Paths.get(rootLocation.toString() + ImageUtil.THUMBNAIL_PATH);

		try {
			return Files.walk(thumbPath, 1).filter(path -> !path.equals(thumbPath));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}
}
