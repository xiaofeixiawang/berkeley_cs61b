/* PixImage.java */

/**
 * The PixImage class represents an image, which is a rectangular grid of color
 * pixels. Each pixel has red, green, and blue intensities in the range 0...255.
 * Descriptions of the methods you must implement appear below. They include a
 * constructor of the form
 *
 * public PixImage(int width, int height);
 *
 * that creates a black (zero intensity) image of the specified width and
 * height. Pixels are numbered in the range (0...width - 1, 0...height - 1).
 *
 * All methods in this class must be implemented to complete Part I. See the
 * README file accompanying this project for additional details.
 */

public class PixImage {

	/**
	 * Define any variables associated with a PixImage object here. These
	 * variables MUST be private.
	 */
	private int width, height;
	private short[][][] image;

	/**
	 * PixImage() constructs an empty PixImage with a specified width and
	 * height. Every pixel has red, green, and blue intensities of zero (solid
	 * black).
	 *
	 * @param width
	 *            the width of the image.
	 * @param height
	 *            the height of the image.
	 */
	public PixImage(int width, int height) {
		this.width = width;
		this.height = height;
		image = new short[3][width][height];
		// Your solution here.
	}

	/**
	 * getWidth() returns the width of the image.
	 *
	 * @return the width of the image.
	 */
	public int getWidth() {
		// Replace the following line with your solution.
		return width;
	}

	/**
	 * getHeight() returns the height of the image.
	 *
	 * @return the height of the image.
	 */
	public int getHeight() {
		// Replace the following line with your solution.
		return height;
	}

	/**
	 * getRed() returns the red intensity of the pixel at coordinate (x, y).
	 *
	 * @param x
	 *            the x-coordinate of the pixel.
	 * @param y
	 *            the y-coordinate of the pixel.
	 * @return the red intensity of the pixel at coordinate (x, y).
	 */
	public short getRed(int x, int y) {
		// Replace the following line with your solution.
		return image[0][x][y];
	}

	/**
	 * getGreen() returns the green intensity of the pixel at coordinate (x, y).
	 *
	 * @param x
	 *            the x-coordinate of the pixel.
	 * @param y
	 *            the y-coordinate of the pixel.
	 * @return the green intensity of the pixel at coordinate (x, y).
	 */
	public short getGreen(int x, int y) {
		// Replace the following line with your solution.
		return image[1][x][y];
	}

	/**
	 * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
	 *
	 * @param x
	 *            the x-coordinate of the pixel.
	 * @param y
	 *            the y-coordinate of the pixel.
	 * @return the blue intensity of the pixel at coordinate (x, y).
	 */
	public short getBlue(int x, int y) {
		// Replace the following line with your solution.
		return image[2][x][y];
	}

	/**
	 * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
	 * and blue intensities.
	 *
	 * If any of the three color intensities is NOT in the range 0...255, then
	 * this method does NOT change any of the pixel intensities.
	 *
	 * @param x
	 *            the x-coordinate of the pixel.
	 * @param y
	 *            the y-coordinate of the pixel.
	 * @param red
	 *            the new red intensity for the pixel at coordinate (x, y).
	 * @param green
	 *            the new green intensity for the pixel at coordinate (x, y).
	 * @param blue
	 *            the new blue intensity for the pixel at coordinate (x, y).
	 */
	public void setPixel(int x, int y, short red, short green, short blue) {
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255)
			return;
		image[0][x][y] = red;
		image[1][x][y] = green;
		image[2][x][y] = blue;
		// Your solution here.
	}

	/**
	 * toString() returns a String representation of this PixImage.
	 *
	 * This method isn't required, but it should be very useful to you when
	 * you're debugging your code. It's up to you how you represent a PixImage
	 * as a String.
	 *
	 * @return a String representation of this PixImage.
	 */
	public String toString() {
		String out="";
		for (int j = 0; j < height; j++) {
			for(int i=0;i<width;i++){
				out+=image[0][i][j]+" ";
			}
			out+="\n";
		}
		// Replace the following line with your solution.
		return out;
	}

	/**
	 * boxBlur() returns a blurred version of "this" PixImage.
	 *
	 * If numIterations == 1, each pixel in the output PixImage is assigned a
	 * value equal to the average of its neighboring pixels in "this" PixImage,
	 * INCLUDING the pixel itself.
	 *
	 * A pixel not on the image boundary has nine neighbors--the pixel itself
	 * and the eight pixels surrounding it. A pixel on the boundary has six
	 * neighbors if it is not a corner pixel; only four neighbors if it is a
	 * corner pixel. The average of the neighbors is the sum of all the neighbor
	 * pixel values (including the pixel itself) divided by the number of
	 * neighbors, with non-integer quotients rounded toward zero (as Java does
	 * naturally when you divide two integers).
	 *
	 * Each color (red, green, blue) is blurred separately. The red input should
	 * have NO effect on the green or blue outputs, etc.
	 *
	 * The parameter numIterations specifies a number of repeated iterations of
	 * box blurring to perform. If numIterations is zero or negative, "this"
	 * PixImage is returned (not a copy). If numIterations is positive, the
	 * return value is a newly constructed PixImage.
	 *
	 * IMPORTANT: DO NOT CHANGE "this" PixImage!!! All blurring/changes should
	 * appear in the new, output PixImage only.
	 *
	 * @param numIterations
	 *            the number of iterations of box blurring.
	 * @return a blurred version of "this" PixImage.
	 */
	public PixImage boxBlur(int numIterations) {
		if (numIterations < 1)
			return this;
		PixImage oldImage = this;
		for (int i = 0; i < numIterations; i++) {
			PixImage newImage = new PixImage(width, height);
			for (int j = 0; j < 3; j++) {
				for (int w = 1; w < width - 1; w++) {
					for (int h = 1; h < height - 1; h++) {
						newImage.image[j][w][h] = (short) ((oldImage.image[j][w - 1][h - 1]
								+ oldImage.image[j][w - 1][h] + oldImage.image[j][w][h + 1]
								+ oldImage.image[j][w + 1][h - 1] + oldImage.image[j][w + 1][h]
								+ oldImage.image[j][w - 1][h + 1] + oldImage.image[j][w][h - 1]
								+ oldImage.image[j][w][h] + oldImage.image[j][w + 1][h + 1]) / 9);
					}
				}
				for (int w = 1; w < width - 1; w++) {
					int h = 0;
					newImage.image[j][w][h] = (short) ((oldImage.image[j][w - 1][h] + oldImage.image[j][w][h]
							+ oldImage.image[j][w+1][h] + oldImage.image[j][w - 1][h + 1] + oldImage.image[j][w][h + 1]
							+ oldImage.image[j][w + 1][h + 1]) / 6);
					h = height - 2;
					newImage.image[j][w][h + 1] = (short) ((oldImage.image[j][w - 1][h] + oldImage.image[j][w][h]
							+ oldImage.image[j][w+1][h] + oldImage.image[j][w - 1][h + 1] + oldImage.image[j][w][h + 1]
							+ oldImage.image[j][w + 1][h + 1]) / 6);
				}
				for (int h = 1; h < height - 1; h++) {
					int w = 0;
					newImage.image[j][w][h] = (short) ((oldImage.image[j][w][h - 1] + oldImage.image[j][w][h]
							+ oldImage.image[j][w][h + 1] + oldImage.image[j][w + 1][h - 1]
							+ oldImage.image[j][w + 1][h] + oldImage.image[j][w + 1][h + 1]) / 6);
					w = width - 2;
					newImage.image[j][w + 1][h] = (short) ((oldImage.image[j][w][h - 1] + oldImage.image[j][w][h]
							+ oldImage.image[j][w][h + 1] + oldImage.image[j][w + 1][h - 1]
							+ oldImage.image[j][w + 1][h] + oldImage.image[j][w + 1][h + 1]) / 6);
				}
				newImage.image[j][0][0] = (short) ((oldImage.image[j][0][0] + oldImage.image[j][0][1]
						+ oldImage.image[j][1][0] + oldImage.image[j][1][1]) / 4);
				newImage.image[j][0][height
						- 1] = (short) ((oldImage.image[j][0][height - 2] + oldImage.image[j][0][height - 1]
								+ oldImage.image[j][1][height - 2] + oldImage.image[j][1][height - 1]) / 4);
				newImage.image[j][width
						- 1][0] = (short) ((oldImage.image[j][width - 2][0] + oldImage.image[j][width - 2][1]
								+ oldImage.image[j][width - 1][0] + oldImage.image[j][width - 1][1]) / 4);
				newImage.image[j][width - 1][height - 1] = (short) ((oldImage.image[j][width - 2][height - 2]
						+ oldImage.image[j][width - 2][height - 1] + oldImage.image[j][width - 1][height - 2]
						+ oldImage.image[j][width - 1][height - 1]) / 4);
			}
			oldImage = newImage;
		}
		// Replace the following line with your solution.
		return oldImage;
	}

	/**
	 * mag2gray() maps an energy (squared vector magnitude) in the range
	 * 0...24,969,600 to a grayscale intensity in the range 0...255. The map is
	 * logarithmic, but shifted so that values of 5,080 and below map to zero.
	 *
	 * DO NOT CHANGE THIS METHOD. If you do, you will not be able to get the
	 * correct images and pass the autograder.
	 *
	 * @param mag
	 *            the energy (squared vector magnitude) of the pixel whose
	 *            intensity we want to compute.
	 * @return the intensity of the output pixel.
	 */
	private static short mag2gray(long mag) {
		short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

		// Make sure the returned intensity is in the range 0...255, regardless
		// of
		// the input value.
		if (intensity < 0) {
			intensity = 0;
		} else if (intensity > 255) {
			intensity = 255;
		}
		return intensity;
	}

	/**
	 * sobelEdges() applies the Sobel operator, identifying edges in "this"
	 * image. The Sobel operator computes a magnitude that represents how strong
	 * the edge is. We compute separate gradients for the red, blue, and green
	 * components at each pixel, then sum the squares of the three gradients at
	 * each pixel. We convert the squared magnitude at each pixel into a
	 * grayscale pixel intensity in the range 0...255 with the logarithmic
	 * mapping encoded in mag2gray(). The output is a grayscale PixImage whose
	 * pixel intensities reflect the strength of the edges.
	 *
	 * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
	 *
	 * @return a grayscale PixImage representing the edges of the input image.
	 *         Whiter pixels represent stronger edges.
	 */
	public PixImage sobelEdges() {
		long gx, gy;
		short[][][] newImage = new short[3][width + 2][height + 2];
		PixImage outImage = new PixImage(width, height);
		for (int j = 0; j < 3; j++) {
			for (int w = 0; w < width; w++) {
				for (int h = 0; h < height; h++) {
					newImage[j][w + 1][h + 1] = image[j][w][h];
				}
			}
			for (int w = 0; w < width; w++) {
				newImage[j][w + 1][0] = newImage[j][w + 1][1];
				newImage[j][w + 1][height + 1] = newImage[j][w + 1][height];
			}
			for (int h = 0; h < height; h++) {
				newImage[j][0][h + 1] = newImage[j][1][h + 1];
				newImage[j][width + 1][h + 1] = newImage[j][width][h + 1];
			}
			newImage[j][0][0] = newImage[j][0][1];
			newImage[j][0][height + 1] = newImage[j][0][height];
			newImage[j][width + 1][0] = newImage[j][width][0];
			newImage[j][width + 1][height + 1] = newImage[j][width][height];
		}
		for (int w = 1; w < width + 1; w++) {
			for (int h = 1; h < height + 1; h++) {
				long energy=0;
				for (int j = 0; j < 3; j++) {
					gx=newImage[j][w-1][h-1]+2*newImage[j][w-1][h]+newImage[j][w-1][h+1]-
							newImage[j][w+1][h-1]-2*newImage[j][w+1][h]-newImage[j][w+1][h+1];
					gy=newImage[j][w-1][h-1]+2*newImage[j][w][h-1]+newImage[j][w+1][h-1]-
							newImage[j][w-1][h+1]-2*newImage[j][w][h+1]-newImage[j][w+1][h+1];
					energy+=(gx*gx+gy*gy);
				}
				for(int j=0;j<3;j++){
					outImage.image[j][w-1][h-1]=mag2gray(energy);
				}
			}
		}
		// Replace the following line with your solution.
		return outImage;
		// Don't forget to use the method mag2gray() above to convert energies
		// to
		// pixel intensities.
	}

	/**
	 * TEST CODE: YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT. You
	 * are welcome to add tests, though. Methods below this point will not be
	 * tested. This is not the autograder, which will be provided separately.
	 */

	/**
	 * doTest() checks whether the condition is true and prints the given error
	 * message if it is not.
	 *
	 * @param b
	 *            the condition to check.
	 * @param msg
	 *            the error message to print if the condition is false.
	 */
	private static void doTest(boolean b, String msg) {
		if (b) {
			System.out.println("Good.");
		} else {
			System.err.println(msg);
		}
	}

	/**
	 * array2PixImage() converts a 2D array of grayscale intensities to a
	 * grayscale PixImage.
	 *
	 * @param pixels
	 *            a 2D array of grayscale intensities in the range 0...255.
	 * @return a new PixImage whose red, green, and blue values are equal to the
	 *         input grayscale intensities.
	 */
	private static PixImage array2PixImage(int[][] pixels) {
		int width = pixels.length;
		int height = pixels[0].length;
		PixImage image = new PixImage(width, height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y], (short) pixels[x][y]);
			}
		}

		return image;
	}

	/**
	 * equals() checks whether two images are the same, i.e. have the same
	 * dimensions and pixels.
	 *
	 * @param image
	 *            a PixImage to compare with "this" PixImage.
	 * @return true if the specified PixImage is identical to "this" PixImage.
	 */
	public boolean equals(PixImage image) {
		int width = getWidth();
		int height = getHeight();

		if (image == null || width != image.getWidth() || height != image.getHeight()) {
			return false;
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (!(getRed(x, y) == image.getRed(x, y) && getGreen(x, y) == image.getGreen(x, y)
						&& getBlue(x, y) == image.getBlue(x, y))) {
					System.out.println(getRed(x,y)+" "+image.getRed(x, y));
					System.out.println(getGreen(x,y)+" "+image.getGreen(x, y));
					System.out.println(getBlue(x,y)+" "+image.getBlue(x, y));
					System.out.println("here has a bug!");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * main() runs a series of tests to ensure that the convolutions (box blur
	 * and Sobel) are correct.
	 */
	public static void main(String[] args) {
		// Be forwarned that when you write arrays directly in Java as below,
		// each "row" of text is a column of your image--the numbers get
		// transposed.
		PixImage image1 = array2PixImage(new int[][] { { 0, 10, 240 }, { 30, 120, 250 }, { 80, 250, 255 } });
		System.out.println("Testing getWidth/getHeight on a 3x3 image.  " + "Input image:");
		System.out.print(image1);
		doTest(image1.getWidth() == 3 && image1.getHeight() == 3, "Incorrect image width and height.");

		System.out.println("Testing blurring on a 3x3 image.");
		doTest(image1.boxBlur(1)
				.equals(array2PixImage(new int[][] { { 40, 108, 155 }, { 81, 137, 187 }, { 120, 164, 218 } })),
				"Incorrect box blur (1 rep):\n" + image1.boxBlur(1));
		doTest(image1.boxBlur(2)
				.equals(array2PixImage(new int[][] { { 91, 118, 146 }, { 108, 134, 161 }, { 125, 151, 176 } })),
				"Incorrect box blur (2 rep):\n" + image1.boxBlur(2));
		doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
				"Incorrect box blur (1 rep + 1 rep):\n" + image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));

		System.out.println("Testing edge detection on a 3x3 image.");
		doTest(image1.sobelEdges()
				.equals(array2PixImage(new int[][] { { 104, 189, 180 }, { 160, 193, 157 }, { 166, 178, 96 } })),
				"Incorrect Sobel:\n" + image1.sobelEdges());

		PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 }, { 0, 0, 100 } });
		System.out.println("Testing getWidth/getHeight on a 2x3 image.  " + "Input image:");
		System.out.print(image2);
		doTest(image2.getWidth() == 2 && image2.getHeight() == 3, "Incorrect image width and height.");

		System.out.println("Testing blurring on a 2x3 image.");
		doTest(image2.boxBlur(1).equals(array2PixImage(new int[][] { { 25, 50, 75 }, { 25, 50, 75 } })),
				"Incorrect box blur (1 rep):\n" + image2.boxBlur(1));

		System.out.println("Testing edge detection on a 2x3 image.");
		doTest(image2.sobelEdges().equals(array2PixImage(new int[][] { { 122, 143, 74 }, { 74, 143, 122 } })),
				"Incorrect Sobel:\n" + image2.sobelEdges());
	}
}
