package edu.csuohio.photomanager.util;

import static org.imgscalr.Scalr.resize;
import static org.imgscalr.Scalr.pad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.imgscalr.Scalr.Mode;

public class ImageUtil {

	public static final String THUMBNAIL_PREFIX = "thumb-";
	public static final String THUMBNAIL_PATH = "/thumbnails/";
	public static final Dimension THUMBNAIL_DIMEN = new Dimension(400, 300);

	/**
	 * Resize an <code>BufferedImage</code> according to the given size.
	 * 
	 * @param originalImage Original <code>BufferedImage</code> image
	 * @param type Image RGB type.
	 * @param width New image width.
	 * @param IMG_HEIGHT New image height.
	 * @return Resized image.
	 */
	public static BufferedImage rescaleAndPadImage(BufferedImage originalImage, int type, int width, int height) {
		int padding = 2;
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		boolean isLandscape = originalImage.getWidth() > originalImage.getHeight();

		Mode mode;
		if (isLandscape) {
			mode = Mode.FIT_TO_WIDTH;
		} else {
			mode = Mode.FIT_TO_HEIGHT;
		}

		BufferedImage scaledImage = resize(originalImage, mode, width - 2 * padding, height - 2 * padding);

		scaledImage = pad(scaledImage, padding, Color.BLACK);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		if (isLandscape) {
			int y = height / 2 - scaledImage.getHeight() / 2;
			g.drawImage(scaledImage, 0, y, null);
		} else {
			int x = width / 2 - scaledImage.getWidth() / 2;
			g.drawImage(scaledImage, x, 0, null);
		}

		g.dispose();

		return resizedImage;
	}

	/**
	 * Generate and save a thumbnail image for the given image.
	 * 
	 * @param path Path of the original image.
	 * @param filename file id of the thumbnail.
	 * @return Whether the operation was successful.
	 */
	public static boolean saveThumbnail(Path path, String filename) {
		BufferedImage originalImage;
		String imageFormat = null;

		try {
			// Read file to a buffered image.
			ImageInputStream imageIn = ImageIO.createImageInputStream(path.toFile());
			Iterator<ImageReader> readers = ImageIO.getImageReaders(imageIn);

			while (readers.hasNext()) {
				ImageReader reader = readers.next();
				imageFormat = reader.getFormatName();
			}

			imageIn.reset();
			originalImage = ImageIO.read(imageIn);

			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

			// Create directory.
			String filePath = path.getParent().toString() + THUMBNAIL_PATH;
			Path thumbPath = Paths.get(filePath);

			if (!Files.exists(thumbPath)) Files.createDirectory(thumbPath);

			BufferedImage resizedImage = ImageUtil.rescaleAndPadImage(originalImage, type, THUMBNAIL_DIMEN.width,
					THUMBNAIL_DIMEN.height);
			ImageIO.write(resizedImage, imageFormat,
					new File(filePath + THUMBNAIL_PREFIX + filename + "." + imageFormat));

			return true;
		} catch (IOException e) {
			System.out.println("Couldn't generate thumbnail for :" + filename);
		}

		return false;
	}
}
