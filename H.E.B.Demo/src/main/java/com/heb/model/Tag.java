package com.heb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * I need to rework this model as it doesn't quite make sense for the following reasons:
 * 
 * 1) A tag's confidence is stored with it, but the confidence is actually tied to the image - NOT the tag. This means
 * that putting the confidence on the tag table strictly is not a good idea.
 * 
 * 2) With my current implementation, the tag name is the PK. That is fine, but I do need to make sure that I check
 * the DB for the existence of a tag if I'm going to use this approach as it is now nearly impossible to insert
 * certain related images. Haha. I'm thinking that I actually want a table that stores an image id, a tag id, and a
 * column for the confidence. In any case, at this point, I'm implementing a code freeze as it's not wise to work on
 * the source code at this point.
 * @author 17084
 *
 */
@Entity

@Table(name = "tag")
public class Tag {

	// This isn't a generated value, so I'll have to figure out validation.
	@Id
	@Column
	private String tag;
	@Column
	private int confidence;

	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Tag(String tag, int confidence) {
		super();
		this.tag = tag;
		this.confidence = confidence;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + confidence;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (confidence != other.confidence)
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tag [tag=" + tag + ", confidence=" + confidence + "]";
	}

}
