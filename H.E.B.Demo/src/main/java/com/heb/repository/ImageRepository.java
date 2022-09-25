package com.heb.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heb.model.Image;
import com.heb.model.Tag;

@Repository("imageRepository")
public interface ImageRepository extends JpaRepository<Image, Integer>{

	List<Image> findByTagsIn(Set<Tag> tags);
}
