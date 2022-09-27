package com.heb.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
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
	// This MockMultipartFile type is Spring's mock implementation of the MultipartFile type. 
	private MockMultipartFile mockFile;

	@BeforeAll
	public void setUpMockito() {
		// Instantiate my object under test
		imageService = new ImageService(imageRepository, s3Client, restTemplate);
		// Initialize my mocks. I might not get around to stubbing before I send this to Jamie, but this setup will be
		//here for the future.
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testIsolateTagsWithSeveralTags() {
		Set<Tag> isolatedTags = imageService.isolateTags("pig,fish,cow,chicken");
		Assertions.assertEquals(4, isolatedTags.size());
		Assertions.assertEquals(true, isolatedTags.contains(new Tag("fish", 0)));
	}
	
	/**
	 * I discovered a bug in the source code with this test. Originally, when I passed an empty string to isolateTags,
	 * it returned a set with a size of 1, which shouldn't be the case. This is the reason why all developers should
	 * test their code; it's easy to make a small mistake or overlook a tiny detail!
	 */
	@Test
	public void testIsolateTagsWithEmptyString() {
		Set<Tag> isolatedTags = imageService.isolateTags(",,");
		Assertions.assertEquals(0, isolatedTags.size());
	}
	
	@Test
	public void testSanitizeImageWithSpaces() {
		mockFile = new MockMultipartFile("file.jpg", "cat and dog.jpg", "image/jpeg", new byte[] {});
		String sanitizedFileName = imageService.sanitizeImageName(mockFile);
		Assertions.assertEquals("cat-and-dog.jpg", sanitizedFileName);
	}
	
	/**
	 * I discovered a bug with this test. I forgot to account for a file with no extension being passed to the
	 * server, so it fails to name my file properly. Ooops.
	 */
	@Test
	public void testSanitizeImageWithNoExtension() {
		mockFile = new MockMultipartFile("file.jpg", "cat and dog", "image/jpeg", new byte[] {});
		String sanitizedFileName = imageService.sanitizeImageName(mockFile);
		Assertions.assertEquals("cat-and-dog.jpg", sanitizedFileName);
	}
	
	@Test
	public void testSanitizeImageWithWrongExtension() {
		mockFile = new MockMultipartFile("file.jpg", "cat-and-dog.png", "image/jpeg", new byte[] {});
		String sanitizedFileName = imageService.sanitizeImageName(mockFile);
		Assertions.assertEquals("cat-and-dog.jpg", sanitizedFileName);
	}

}
