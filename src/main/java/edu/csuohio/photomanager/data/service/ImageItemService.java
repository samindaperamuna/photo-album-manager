package edu.csuohio.photomanager.data.service;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import edu.csuohio.photomanager.data.ImageItem;

@Service
public interface ImageItemService extends MongoRepository<ImageItem, Integer> {

	ImageItem findById(Serializable id);
}
