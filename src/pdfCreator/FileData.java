package pdfCreator;

/**
 * Stores the necessary data for an image or pdf file
 * 
 * @author pro
 */
public class FileData {
	/*
	 * Path of the file
	 */
	private String path;
	/*
	 * Width of image, if it is an image file, else it is the original width
	 */
	private float img_width;
	/*
	 * Height of image, if it is an image file, else it is the original height
	 */
	private float img_height;
	
	/**
	 * Creates FileData with filepath and width and height
	 * 
	 * @param path
	 *            Path of the file
	 * @param img_width
	 *            Width of the image, if it is an image
	 * @param img_height
	 *            Height of the image, if it is an image
	 */
	public FileData(String path, float img_width, float img_height) {
		this.path = path;
		this.img_width = img_width;
		this.img_height = img_height;
	}
	
	/**
	 * @return Path of the file
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @return Whether the file is a pdf
	 */
	public boolean isPdf() {
		if (path.substring(path.length() - 3).equals("pdf")) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return width of image
	 */
	public float getImageWidth() {
		return img_width;
	}
	
	/**
	 * @return height of image
	 */
	public float getImageHeight() {
		return img_height;
	}
}
