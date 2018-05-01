package edu.csuohio.photomanager.data;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class is used to save image details as a document.It extends
 * <code>BaseItem</code> to obtain an accessible id field within the spring
 * context.
 */
@Document(collection = "image-items")
public class ImageItem extends BaseDocument<String> {

	String originalName;
	String imageName;
	String imageDescription;

	public ImageItem(String originalName, String imageDescription) {
		super();

		this.originalName = originalName;
		this.imageDescription = imageDescription;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((imageDescription == null) ? 0 : imageDescription.hashCode());
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((originalName == null) ? 0 : originalName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		ImageItem other = (ImageItem) obj;
		if (imageDescription == null) {
			if (other.imageDescription != null) return false;
		} else if (!imageDescription.equals(other.imageDescription)) return false;
		if (imageName == null) {
			if (other.imageName != null) return false;
		} else if (!imageName.equals(other.imageName)) return false;
		if (originalName == null) {
			if (other.originalName != null) return false;
		} else if (!originalName.equals(other.originalName)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImageItem [originalName=" + originalName + ", imageName=" + imageName + ", imageDescription="
				+ imageDescription + "]";
	}
}