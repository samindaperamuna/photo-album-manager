package edu.csuohio.photomanager.data.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Base class for file system storages.
 */
public interface StorageService {
	void init();

	void store(MultipartFile file);

	Stream<Path> loadAll();

	Stream<Path> loadThumbnails();

	Path load(String fileName);

	Resource loadAsResource(String filename);

	void deleteAll();
}
