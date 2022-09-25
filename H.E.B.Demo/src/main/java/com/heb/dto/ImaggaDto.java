package com.heb.dto;

import java.util.Arrays;

public class ImaggaDto {

	private Result result;
	private Status status;

	/**
	 * This class represents the most inner text for a JSON "tag" object. Note that
	 * this class and the other static inner classes of the ImaggaDto type are
	 * static to allow the Jackson Object Mapper to deserialize the Imagga API's
	 * response payload as non-static inner classes do not have no-args constructors
	 * that Jackson can use for deserialization.
	 * 
	 * @author 17084
	 *
	 */
	public static class TagText {
		private String en;

		public TagText() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getEn() {
			return en;
		}

		public void setEn(String en) {
			this.en = en;
		}

		@Override
		public String toString() {
			return "TagText [en=" + en + "]";
		}

	}

	/**
	 * This class represents an object in the responses "tags" array. Each object
	 * has a numeric confidence field and an inner TagText field.
	 * 
	 * @author 17084
	 *
	 */
	public static class TagDto {
		private int confidence;
		private TagText tag;

		public TagDto() {
			super();
			// TODO Auto-generated constructor stub
		}

		public int getConfidence() {
			return confidence;
		}

		public void setConfidence(int confidence) {
			this.confidence = confidence;
		}

		public TagText getTag() {
			return tag;
		}

		public void setTag(TagText tag) {
			this.tag = tag;
		}

		@Override
		public String toString() {
			return "TagDto [confidence=" + confidence + ", tag=" + tag + "]";
		}

	}

	/**
	 * This class represents the "result" portion of the Imagga payload.
	 * 
	 * @author 17084
	 *
	 */
	public static class Result {

		private TagDto[] tags;

		public Result() {
			super();
		}

		public Result(TagDto[] tags) {
			super();
			this.tags = tags;
		}

		public TagDto[] getTags() {
			return tags;
		}

		public void setTags(TagDto[] tags) {
			this.tags = tags;
		}

		@Override
		public String toString() {
			return "Result [tags=" + Arrays.toString(tags) + "]";
		}

	}

	/**
	 * This class represents the "status" portion of the Imagga payload. It is not
	 * used locally, but I did not have the heart not to complete the DTO just in
	 * case I needed this portion of the payload later down the line.
	 * 
	 * @author 17084
	 *
	 */
	public static class Status {
		private String type;
		private String text;

		public Status() {
			super();
		}

		public Status(String type, String text) {
			super();
			this.type = type;
			this.text = text;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return "Status [type=" + type + ", text=" + text + "]";
		}

	}

	public ImaggaDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImaggaDto(Result result, Status status) {
		super();
		this.result = result;
		this.status = status;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		ImaggaDto other = (ImaggaDto) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImaggaDto [result=" + result + ", status=" + status + "]";
	}
}
