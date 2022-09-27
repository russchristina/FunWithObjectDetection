package com.heb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.heb.model.Image;
import com.heb.service.ImageService;

@RestController("imageController")
@RequestMapping("/images")
public class ImageController {
	
	private ImageService imageService;
	
	@Autowired
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	/**
	 * 
	 * @param tags the objects associated with the image the client is looking for
	 * @return a list of images
	 */
	@GetMapping(path = "", produces = "application/json")
	public ResponseEntity<List<Image>> findAll(@RequestParam(required = false, name = "objects") String tags){
		return (tags == null) ? 
				new ResponseEntity<List<Image>>(imageService.findAll(), HttpStatus.OK):
					new ResponseEntity<List<Image>>(imageService.findByTagsIn(tags), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Image> findByImageId(@PathVariable int id) {
		return ResponseEntity.of(imageService.findById(id));
	}
	
	/**
	 * This method accepts a post request. I've discovered a bug here. I made tags unique (using their names as PKs), 
	 * so if a tag already exists within my DB, an exception is thrown. I should rethink my schema to avoid issues
	 * like this. For instance, checking for the existence of the tag and pulling its ID first would be helpful here.
	 * @param image
	 * @param label
	 * @param objectDetection
	 * @return
	 */
	@PostMapping(path = "", consumes = "multipart/form-data")
	public ResponseEntity<Image> save(@RequestParam("image") MultipartFile image, @RequestParam(required = false) String label, @RequestParam(required = false) boolean objectDetection) {
		return new ResponseEntity<Image>(imageService.save(image, label, objectDetection), HttpStatus.CREATED);
	}
	
}
