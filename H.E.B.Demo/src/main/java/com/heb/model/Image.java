package com.heb.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity

@Table(name = "image")
public class Image {

	// Unique identifier for the image
	@Id
	@Column(name = "image_id")
	@GeneratedValue(generator = "id_seq_generator", strategy = GenerationType.AUTO)
	@SequenceGenerator(allocationSize = 1, name = "id_seq_generator")
	private int id;
	// The URL for the image
	@Column(name = "image_url", nullable = false)
	private String url;
	// A label provided by the client (auto-generated if not provided)
	@Column(name = "image_label", nullable = false)
	private String label;
	// Field indicating whether or not object detection should be used for this
	// image
	@Column(name = "object_detection")
	private boolean enabledObjectDetection;
	// Set of the objects that are associated with this image
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "image_tag")
	private Set<Tag> tags;

	public Image() {
		super();
	}
	
	public Image(String url, String label, boolean enabledObjectDetection, Set<Tag> tags) {
		super();
		this.url = url;
		this.label = label;
		this.enabledObjectDetection = enabledObjectDetection;
		this.tags = tags;
	}

	public Image(int id, String url, String label, boolean enabledObjectDetection, Set<Tag> tags) {
		this(url, label, enabledObjectDetection, tags);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isEnabledObjectDetection() {
		return enabledObjectDetection;
	}

	public void setEnabledObjectDetection(boolean enabledObjectDetection) {
		this.enabledObjectDetection = enabledObjectDetection;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabledObjectDetection ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Image other = (Image) obj;
		if (enabledObjectDetection != other.enabledObjectDetection)
			return false;
		if (id != other.id)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", url=" + url + ", label=" + label + ", enabledObjectDetection="
				+ enabledObjectDetection + ", tags=" + tags + "]";
	}
}
