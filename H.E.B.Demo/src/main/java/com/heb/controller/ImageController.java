package com.heb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public List<Image> findAll(@RequestParam(required = false, name = "objects") String tags){
		return (tags == null) ? 
				this.imageService.findAll():this.imageService.findByTagsIn(tags);
	}
	
	@GetMapping(path = "/{id}")
	public Image findByImageId(@PathVariable int id) {
		//I should probably put some validation here or do some exception handling.
		return this.imageService.findById(id).orElse(null);
	}
	
	@PostMapping(path = "", consumes = "multipart/form-data")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Image save(@RequestParam("image") MultipartFile image, @RequestParam(required = false) String label, @RequestParam(required = false) boolean objectDetection) {
		return this.imageService.save(image, label, objectDetection);
	}
	
}
