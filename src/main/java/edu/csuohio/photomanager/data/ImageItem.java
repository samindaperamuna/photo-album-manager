package edu.csuohio.photomanager.data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "image-items")
public class ImageItem extends BaseDocument<Integer> {

	String imageTitle;
	String imageDescription;
	String imagePath;

	public ImageItem(String imageTitle, String imageDescription, String imagePath) {
		super();
		this.imageTitle = imageTitle;
		this.imageDescription = imageDescription;
		this.imagePath = imagePath;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((imageDescription == null) ? 0 : imageDescription.hashCode());
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((imageTitle == null) ? 0 : imageTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageItem other = (ImageItem) obj;
		if (imageDescription == null) {
			if (other.imageDescription != null)
				return false;
		} else if (!imageDescription.equals(other.imageDescription))
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (imageTitle == null) {
			if (other.imageTitle != null)
				return false;
		} else if (!imageTitle.equals(other.imageTitle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImageItem [imageTitle=" + imageTitle + ", imageDescription=" + imageDescription + ", imagePath="
				+ imagePath + "]";
	}
}