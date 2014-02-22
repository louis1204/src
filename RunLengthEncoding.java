/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Iterator;

public class RunLengthEncoding implements Iterable{

  private DList1 run;
  private int height, width;
  
  public RunLengthEncoding(int width, int height) {
    int[] black = new int[4];
    black[0] = 0;	//r
    black[1] = 0;	//g
    black[2] = 0;	//b
    black[3] = width * height; //length of run
    this.height = height;
    this.width = width;
    run = new DList1(black);
  }

  public RunLengthEncoding(int width, int height, int[] red, int[] green, int[] blue, int[] runLengths) {
    int[] subRun = new int[4];
    run = new DList1();
    int sum = 0;
    this.height = height;
    this.width = width;
    //going backwards since we only have insertFront
    for(int i = runLengths.length - 1; i > -1; i--)
    {
    	subRun[0] = red[i];	//r
    	subRun[1] = green[i];	//g
    	subRun[2] = blue[i];	//b
    	subRun[3] = runLengths[i];	//run length
    	run.insertFront(subRun);	//insert the run to the dlist1
    	sum = sum + runLengths[i];	//summing up the # of elements
    }
    
    if(sum != width * height)
    {
    	System.out.println("Sum didn't match up");
    	System.exit(-1);	//exit if the sum doesn't match up
    }
  }

	@Override
	public RunIterator iterator() {
		return new RunIterator(run.head);
	}
	
	//getter methods
	public int getWidth() {
	    return this.width;
	  }
	
	public int getHeight() {
	    return this.height;
	  }

	/**
	   *  toPixImage() converts a run-length encoding of an image into a PixImage
	   *  object.
	   *
	   *  @return the PixImage that this RunLengthEncoding encodes.
	   */
	  public PixImage toPixImage() {
	    PixImage pi =  new PixImage(this.width, this.height);	//set up piximage with width and height
	    
	    RunIterator runLengthIterator = this.iterator();	//set up the iterator
	    
	    int[] item;
	    int x = 0;	//counter for column
	    int heightCounter = 0; //counter for height
	    while(runLengthIterator.hasNext())
	    {
	    	item = runLengthIterator.next();
	    	for(int i = 0; i < item[3]; i++)	//for the run length
	    	{
	    		if(heightCounter > this.height - 1)	//if height exceeds the width of image
		    	{
		    		x++;	//increment x
		    		heightCounter = 0;	//reset counter
		    	}
	    		
	    		pi.setPixel(x, heightCounter, (short)item[0], (short)item[1], (short)item[2]); //set pixel    		
	    	}
	    }
	    return pi;
	  }

	  /**
	   *  toString() returns a String representation of this RunLengthEncoding.
	   *
	   *  This method isn't required, but it should be very useful to you when
	   *  you're debugging your code.  It's up to you how you represent
	   *  a RunLengthEncoding as a String.
	   *
	   *  @return a String representation of this RunLengthEncoding.
	   */
	  public String toString() {
	    // Replace the following line with your solution.
	    return "";
	  }


	  /**
	   *  The following methods are required for Part III.
	   */

	  /**
	   *  RunLengthEncoding() (with one parameter) is a constructor that creates
	   *  a run-length encoding of a specified PixImage.
	   * 
	   *  Note that you must encode the image in row-major format, i.e., the second
	   *  pixel should be (1, 0) and not (0, 1).
	   *
	   *  @param image is the PixImage to run-length encode.
	   */
	  public RunLengthEncoding(PixImage image) {
		int[] subRun = new int[4];
		int currRed = image.getRed(image.getWidth() - 1, image.getHeight() - 1);	//get the very last element's rgb
		int currGreen = image.getGreen(image.getWidth() - 1, image.getHeight() - 1);
		int currBlue = image.getBlue(image.getWidth() - 1, image.getHeight() - 1);
		int runLength = 0;	//since last element is at least 1 run
		
		run = new DList1();
	    // Again we go backwards
		for(int column = image.getWidth() - 1; column > -1; column--)
		{
			for(int row = image.getHeight() - 1; row > -1; row--)
			{
				if(currRed == image.getRed(column, row) &&
						currGreen == image.getGreen(column, row) &&
						currBlue == image.getBlue(column, row))
				{
					runLength++;
				}
				else
				{	//no longer the same
					subRun[0] = currRed;	//r
			    	subRun[1] = currGreen;	//g
			    	subRun[2] = currBlue;	//b
			    	subRun[3] = runLength;	//run length
			    	run.insertFront(subRun);	//insert the run to the dlist1
			    	
			    	currRed = image.getRed(column, row);	//reset current values and run
			    	currGreen = image.getGreen(column, row);
			    	currBlue = image.getBlue(column, row);
			    	runLength = 1;
				}
			}
		}
	    check();
	  }

	  /**
	   *  check() walks through the run-length encoding and prints an error message
	   *  if two consecutive runs have the same RGB intensities, or if the sum of
	   *  all run lengths does not equal the number of pixels in the image.
	   */
	  public void check() {
		int[] item;
	    RunIterator ri = this.iterator();
	    int previousRed = -1;	//previous rgb value
	    int previousGreen = -1;	//init to -1 to ensure initially different
	    int previousBlue = -1;
	    
	    while(ri.hasNext())
	    {
	    	item = ri.next();
	    	if(item[0] == previousRed &&
	    			item[1] == previousGreen &&
	    			item[2] == previousBlue)
	    	{
	    		System.out.print("Same elements in run length encoding");
	    	}
	    	
	    	previousRed = item[0];
	    	previousGreen = item[1];
	    	previousBlue = item[2];
	    }
	  }
	  
	  /**
	   * setAndCheckRLE() sets the given coordinate in the given run-length
	   * encoding to the given value and then checks whether the resulting
	   * run-length encoding is correct.
	   *
	   * @param rle the run-length encoding to modify.
	   * @param x the x-coordinate to set.
	   * @param y the y-coordinate to set.
	   * @param intensity the grayscale intensity to assign to pixel (x, y).
	   */
	  private static void setAndCheckRLE(RunLengthEncoding rle,
	                                     int x, int y, int intensity) {
	    rle.setPixel(x, y, (short) intensity, (short) intensity, (short) intensity);
	    rle.check();
	  }
	  
	  /**
	   *  The following method is required for Part IV.
	   */

	  /**
	   *  setPixel() modifies this run-length encoding so that the specified color
	   *  is stored at the given (x, y) coordinates.  The old pixel value at that
	   *  coordinate should be overwritten and all others should remain the same.
	   *  The updated run-length encoding should be compressed as much as possible;
	   *  there should not be two consecutive runs with exactly the same RGB color.
	   *
	   *  @param x the x-coordinate of the pixel to modify.
	   *  @param y the y-coordinate of the pixel to modify.
	   *  @param red the new red intensity to store at coordinate (x, y).
	   *  @param green the new green intensity to store at coordinate (x, y).
	   *  @param blue the new blue intensity to store at coordinate (x, y).
	   */
	  public void setPixel(int x, int y, short red, short green, short blue) {
	    // Your solution here, but you should probably leave the following line
	    //   at the end.
	    check();
	  }

	  /**
	   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
	   * You are welcome to add tests, though.  Methods below this point will not
	   * be tested.  This is not the autograder, which will be provided separately.
	   */


	  /**
	   * doTest() checks whether the condition is true and prints the given error
	   * message if it is not.
	   *
	   * @param b the condition to check.
	   * @param msg the error message to print if the condition is false.
	   */
	  private static void doTest(boolean b, String msg) {
	    if (b) {
	      System.out.println("Good.");
	    } else {
	      System.err.println(msg);
	    }
	  }

	  /**
	   * array2PixImage() converts a 2D array of grayscale intensities to
	   * a grayscale PixImage.
	   *
	   * @param pixels a 2D array of grayscale intensities in the range 0...255.
	   * @return a new PixImage whose red, green, and blue values are equal to
	   * the input grayscale intensities.
	   */
	  private static PixImage array2PixImage(int[][] pixels) {
	    int width = pixels.length;
	    int height = pixels[0].length;
	    PixImage image = new PixImage(width, height);

	    for (int x = 0; x < width; x++) {
	      for (int y = 0; y < height; y++) {
	        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
	                       (short) pixels[x][y]);
	      }
	    }

	    return image;
	  }
	  
	  /**
	   * main() runs a series of tests of the run-length encoding code.
	   */
	  public static void main(String[] args) {
	    // Be forwarned that when you write arrays directly in Java as below,
	    // each "row" of text is a column of your image--the numbers get
	    // transposed.
	    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
	                                                   { 1, 4, 7 },
	                                                   { 2, 5, 8 } });

	    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
	                       "on a 3x3 image.  Input image:");
	    System.out.print(image1);
	    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
	    rle1.check();
	    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
	    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
	           "RLE1 has wrong dimensions");

	    System.out.println("Testing toPixImage() on a 3x3 encoding.");
	    doTest(image1.equals(rle1.toPixImage()),
	           "image1 -> RLE1 -> image does not reconstruct the original image");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle1, 0, 0, 42);
	    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
	    doTest(rle1.toPixImage().equals(image1),
	           /*
	                       array2PixImage(new int[][] { { 42, 3, 6 },
	                                                    { 1, 4, 7 },
	                                                    { 2, 5, 8 } })),
	           */
	           "Setting RLE1[0][0] = 42 fails.");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle1, 1, 0, 42);
	    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
	    doTest(rle1.toPixImage().equals(image1),
	           "Setting RLE1[1][0] = 42 fails.");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle1, 0, 1, 2);
	    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
	    doTest(rle1.toPixImage().equals(image1),
	           "Setting RLE1[0][1] = 2 fails.");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle1, 0, 0, 0);
	    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
	    doTest(rle1.toPixImage().equals(image1),
	           "Setting RLE1[0][0] = 0 fails.");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle1, 2, 2, 7);
	    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
	    doTest(rle1.toPixImage().equals(image1),
	           "Setting RLE1[2][2] = 7 fails.");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle1, 2, 2, 42);
	    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
	    doTest(rle1.toPixImage().equals(image1),
	           "Setting RLE1[2][2] = 42 fails.");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle1, 1, 2, 42);
	    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
	    doTest(rle1.toPixImage().equals(image1),
	           "Setting RLE1[1][2] = 42 fails.");


	    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
	                                                   { 2, 4, 5 },
	                                                   { 3, 4, 6 } });

	    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
	                       "on another 3x3 image.  Input image:");
	    System.out.print(image2);
	    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
	    rle2.check();
	    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
	    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
	           "RLE2 has wrong dimensions");

	    System.out.println("Testing toPixImage() on a 3x3 encoding.");
	    doTest(rle2.toPixImage().equals(image2),
	           "image2 -> RLE2 -> image does not reconstruct the original image");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle2, 0, 1, 2);
	    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
	    doTest(rle2.toPixImage().equals(image2),
	           "Setting RLE2[0][1] = 2 fails.");

	    System.out.println("Testing setPixel() on a 3x3 encoding.");
	    setAndCheckRLE(rle2, 2, 0, 2);
	    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
	    doTest(rle2.toPixImage().equals(image2),
	           "Setting RLE2[2][0] = 2 fails.");


	    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
	                                                   { 1, 6 },
	                                                   { 2, 7 },
	                                                   { 3, 8 },
	                                                   { 4, 9 } });

	    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
	                       "on a 5x2 image.  Input image:");
	    System.out.print(image3);
	    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
	    rle3.check();
	    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
	    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
	           "RLE3 has wrong dimensions");

	    System.out.println("Testing toPixImage() on a 5x2 encoding.");
	    doTest(rle3.toPixImage().equals(image3),
	           "image3 -> RLE3 -> image does not reconstruct the original image");

	    System.out.println("Testing setPixel() on a 5x2 encoding.");
	    setAndCheckRLE(rle3, 4, 0, 6);
	    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
	    doTest(rle3.toPixImage().equals(image3),
	           "Setting RLE3[4][0] = 6 fails.");

	    System.out.println("Testing setPixel() on a 5x2 encoding.");
	    setAndCheckRLE(rle3, 0, 1, 6);
	    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
	    doTest(rle3.toPixImage().equals(image3),
	           "Setting RLE3[0][1] = 6 fails.");

	    System.out.println("Testing setPixel() on a 5x2 encoding.");
	    setAndCheckRLE(rle3, 0, 0, 1);
	    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
	    doTest(rle3.toPixImage().equals(image3),
	           "Setting RLE3[0][0] = 1 fails.");


	    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
	                                                   { 1, 4 },
	                                                   { 2, 5 } });

	    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
	                       "on a 3x2 image.  Input image:");
	    System.out.print(image4);
	    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
	    rle4.check();
	    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
	    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
	           "RLE4 has wrong dimensions");

	    System.out.println("Testing toPixImage() on a 3x2 encoding.");
	    doTest(rle4.toPixImage().equals(image4),
	           "image4 -> RLE4 -> image does not reconstruct the original image");

	    System.out.println("Testing setPixel() on a 3x2 encoding.");
	    setAndCheckRLE(rle4, 2, 0, 0);
	    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
	    doTest(rle4.toPixImage().equals(image4),
	           "Setting RLE4[2][0] = 0 fails.");

	    System.out.println("Testing setPixel() on a 3x2 encoding.");
	    setAndCheckRLE(rle4, 1, 0, 0);
	    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
	    doTest(rle4.toPixImage().equals(image4),
	           "Setting RLE4[1][0] = 0 fails.");

	    System.out.println("Testing setPixel() on a 3x2 encoding.");
	    setAndCheckRLE(rle4, 1, 0, 1);
	    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
	    doTest(rle4.toPixImage().equals(image4),
	           "Setting RLE4[1][0] = 1 fails.");
	  }
}
