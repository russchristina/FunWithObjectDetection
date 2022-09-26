package com.heb.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.heb.model.Image;
import com.heb.model.Tag;
import com.heb.repository.ImageRepository;

import software.amazon.awssdk.services.s3.S3Client;

@TestInstance(Lifecycle.PER_CLASS)
public class ImageServiceTest {

	@Mock
	private ImageRepository imageRepository;
	@Mock
	private S3Client s3Client;
	@Mock
	private RestTemplate restTemplate;
	@InjectMocks
	private ImageService imageService;
	// Dummy tags for tests
	private Set<Tag> mockTags;
	// Dummy images for tests
	private List<Image> mockImages;

	@BeforeAll
	public void setUpMockito() {
		// Instantiate my object under test
		imageService = new ImageService(imageRepository, s3Client, restTemplate);
		// Initialize my mocks
		MockitoAnnotations.openMocks(this);
	}

	@BeforeAll
	public void populateMockResources() {
		// Populate dummy tags
		mockTags = new HashSet<>();
		mockTags.add(new Tag("cat", 100));
		mockTags.add(new Tag("dog", 50));
		//Populate dummy images
		mockImages.add(new Image(1, "url", "label", false, new HashSet<>()));
		mockImages.add(new Image(2, "url", "label", false, mockTags));
	}

	@Test
	public void testIsolateTags() {
		
	}

}
