package com.heb.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.heb.dto.ImaggaDto;
import com.heb.dto.ImaggaDto.TagDto;
import com.heb.model.Image;
import com.heb.model.Tag;
import com.heb.repository.ImageRepository;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service("imageService")
public class ImageService {

	private ImageRepository imageRepository;
	private S3Client s3Client;
	private RestTemplate restTemplate;
	private static final String s3Location = "https://christina-s3.s3.us-east-2.amazonaws.com";
	private static final String imaggaResource = "https://api.imagga.com/v2/tags?image_url=";

	@Autowired
	public ImageService(ImageRepository imageRepository, S3Client s3Client, RestTemplate restTemplate) {
		this.imageRepository = imageRepository;
		this.s3Client = s3Client;
		this.restTemplate = restTemplate;
	}

	public List<Image> findAll() {
		return this.imageRepository.findAll();
	}

	public List<Image> findByTagsIn(String tags) {
		String[] individualTags = tags.split(",");
		Set<Tag> setOfTags = new HashSet<>();
		for (String tag : individualTags) {
			setOfTags.add(new Tag(tag, 0));
		}

		return this.imageRepository.findByTagsIn(setOfTags);
	}

	public Optional<Image> findById(int id) {
		return this.imageRepository.findById(id);
	}
	
	public Image save(MultipartFile image, String label, boolean objectDetection) {
		uploadImage(image);
		Set<Tag> extractedTags = (objectDetection) ? extractTags(image):null;
		Image savedImage = this.imageRepository.save(
				new Image(
						s3Location + "/" + sanitizeImageName(image), 
						(label == null) ? image.getOriginalFilename() : label,
						objectDetection, 
						extractedTags)
				);
		return savedImage;
		
	}
	
	public String sanitizeImageName(MultipartFile image) {
		String fileName = image.getOriginalFilename();
		fileName = fileName.contains(" ") ? fileName.replace(" ", "-") : fileName;
		fileName = !fileName.endsWith(".jpg") ? fileName.replaceAll("\\.[a-zA-Z]+", ".jpg") : fileName;
		return fileName;

	}
	
	/**
	 * My current strategy for label generation is lazy as it just uses the file's name minus
	 * the extension by splitting around the "." before the extension and returning the first
	 * element of the array, which should be the file name. It's lazy, but at the very least it
	 * accounts for any file extension.
	 * @param image The image that the client has uploaded.
	 * @return
	 */
	public String generateLabel(MultipartFile image) {
		return image.getOriginalFilename().split(".")[0];
	}
	
	public Set<Tag> extractTags(MultipartFile image){
		HashSet<Tag> extractedTags = new HashSet<>();
		ImaggaDto imaggaPayload = this.fetchTags(image);
		TagDto[] tags = imaggaPayload.getResult().getTags();
		for(TagDto tagDto : tags) {
			extractedTags.add(new Tag(tagDto.getTag().getEn(), tagDto.getConfidence()));
		}
		return extractedTags;
	}

	/*
	 * My AWS access key and secret are handled as environment variables on my machine to keep
	 * them safe.
	 */
	public void uploadImage(MultipartFile image) {
		PutObjectRequest request = PutObjectRequest.builder().bucket("christina-s3").
				contentType("image/jpeg")
				.key(sanitizeImageName(image)).build();

		try {
			this.s3Client.putObject(request, RequestBody.fromBytes(image.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ImaggaDto fetchTags(MultipartFile image) {
		ResponseEntity<ImaggaDto> response = this.restTemplate.getForEntity(URI.create(imaggaResource + s3Location + "/" + image.getOriginalFilename()), ImaggaDto.class);
		return response.getBody();
	}
}
