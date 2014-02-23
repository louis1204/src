/* PixImage.java */

/**
 *  The PixImage class represents an image, which is a rectangular grid of
 *  color pixels.  Each pixel has red, green, and blue intensities in the range
 *  0...255.  Descriptions of the methods you must implement appear below.
 *  They include a constructor of the form
 *
 *      public PixImage(int width, int height);
 *
 *  that creates a black (zero intensity) image of the specified width and
 *  height.  Pixels are numbered in the range (0...width - 1, 0...height - 1).
 *
 *  All methods in this class must be implemented to complete Part I.
 *  See the README file accompanying this project for additional details.
 */

public class PixImage {

  /**
   *  Define any variables associated with a PixImage object here.  These
   *  variables MUST be private.
   */
  private int w; // w is the # of rows - namely 1st dim - correspond to y
  private int h; // h is the # of colums - namely 2nd dim - correspond to x
  //private int red; 
  //private int green;
  //private int blue; 
  private short[][][] loc;
  // 1st item in 3rd dim in array
  // 2nd item in 3rd dim in array
  // 3rd item in 3rd dim in array 
  /**
   * PixImage() constructs an empty PixImage with a specified width and height.
   * Every pixel has red, green, and blue intensities of zero (solid black).
   *
   * @param width the width of the image.
   * @param height the height of the image.
   */
  public PixImage(int width, int height) {
    w = width;
    h  = height;
    loc = new short[h][w][3];
    for (int a=0; a<h; a++) {
      for (int b=0; b<w; b++) {
        loc[a][b][0] = 0;
        loc[a][b][1] = 0;
        loc[a][b][2] = 0;
      }
    }
    // Your solution here.
  }

  /**
   * getWidth() returns the width of the image.
   *
   * @return the width of the image.
   */
  public int getWidth() {
    // Replace the following line with your solution.
    return w;
  }

  /**
   * getHeight() returns the height of the image.
   *
   * @return the height of the image.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return h;
  }

  /**
   * getRed() returns the red intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the red intensity of the pixel at coordinate (x, y).
   */
  public short getRed(int x,int y) {
    // Replace the following line with your solution.
    return loc[y][x][0];
  }

  /**
   * getGreen() returns the green intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the green intensity of the pixel at coordinate (x, y).
   */
  public short getGreen(int x,int y) {
    // Replace the following line with your solution.
      return loc[y][x][1];
  }

  /**
   * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the blue intensity of the pixel at coordinate (x, y).
   */
  public short getBlue(int x,int y) {
    // Replace the following line with your solution.
    return loc[y][x][2];
  }

  /**
   * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
   * and blue intensities.
   *
   * If any of the three color intensities is NOT in the range 0...255, then
   * this method does NOT change any of the pixel intensities.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param red the new red intensity for the pixel at coordinate (x, y).
   * @param green the new green intensity for the pixel at coordinate (x, y).
   * @param blue the new blue intensity for the pixel at coordinate (x, y).
   */
  public void setPixel(int x,int y,short red,short green,short blue) {
    //boolean valid_x = (x<=h-1 && x>=0);
    //boolean valid_y = (y<=w-1 && y>=0);
    //if (valid_x && valid_y) {
        
        if (red>=0 && red<=255) {
          loc[y][x][0] = red; 
          if (green>=0 && green<=255) {
            loc[y][x][1] = green;
            if (blue>=0 && blue<=255) {
              loc[y][x][2] = blue;
            }
          }
        }
    //} else {
    //  System.out.println("please input valid coordinates");
    //}
    
    // Your solution here.
  }

  /**
   * toString() returns a String representation of this PixImage.
   *
   * This method isn't required, but it should be very useful to you when
   * you're debugging your code.  It's up to you how you represent a PixImage
   * as a String.
   *
   * @return a String representation of this PixImage.
   */
  public String toString() {
    String s = "";
    for (int htemp=0; htemp<h; htemp++) {
      for (int wtemp=0; wtemp<w; wtemp++) {
        s += " ";
        for (int i=0; i<3; i++) {
          s += loc[htemp][wtemp][i];
        }
      }
      s += "\n";
    }
    return s;
  }

  /**
   * boxBlur() returns a blurred version of "this" PixImage.
   *
   * If numIterations == 1, each pixel in the output PixImage is assigned
   * a value equal to the average of its neighboring pixels in "this" PixImage,
   * INCLUDING the pixel itself.
   *
   * A pixel not on the image boundary has nine neighbors--the pixel itself and
   * the eight pixels surrounding it.  A pixel on the boundary has six
   * neighbors if it is not a corner pixel; only four neighbors if it is
   * a corner pixel.  The average of the neighbors is the sum of all the
   * neighbor pixel values (including the pixel itself) divided by the number
   * of neighbors, with non-integer quotients rounded toward zero (as Java does
   * naturally when you divide two integers).
   *
   * Each color (red, green, blue) is blurred separately.  The red input should
   * have NO effect on the green or blue outputs, etc.
   *
   * The parameter numIterations specifies a number of repeated iterations of
   * box blurring to perform.  If numIterations is zero or negative, "this"
   * PixImage is returned (not a copy).  If numIterations is positive, the
   * return value is a newly constructed PixImage.
   *
   * IMPORTANT:  DO NOT CHANGE "this" PixImage!!!  All blurring/changes should
   * appear in the new, output PixImage only.
   *
   * @param numIterations the number of iterations of box blurring.
   * @return a blurred version of "this" PixImage.
   */
  public PixImage boxBlur(int numIterations) {

    int temp_w = this.getWidth();
    int temp_h = this.getHeight();
    PixImage finalPI = new PixImage(temp_w, temp_h); // The constructor will know how to create w/ w be y-coord, h by x-coord
    PixImage copyPI = new PixImage(temp_w, temp_h);

    for (int hei=0; hei<temp_h; hei++) {
      for (int wei=0; wei<temp_w; wei++) {
        copyPI.setPixel(wei, hei, getRed(wei,hei),getGreen(wei,hei),getBlue(wei,hei));
      }
    }

    while (numIterations>0) { // we deal with the pixels that have 8 neights in loops and treat others as special cases
    
      for (int y=0; y<temp_h; y++) {
        for (int x=0; x<temp_w; x++) {
          short newRed = 0;
          short newGreen = 0;
          short newBlue = 0;
          int count = 0;
          for (int d=-1; d<=1; d++) {
              if (y+d>=0 && y+d<temp_h) {
                for (int d2=-1; d2<=1; d2++) {
                  if (x+d2>=0 && x+d2<temp_w) {
                    newRed = (short)(newRed+copyPI.getRed(x+d2,y+d));
                    newGreen = (short)(newGreen+copyPI.getGreen(x+d2,y+d));
                    newBlue = (short)(newBlue+copyPI.getBlue(x+d2,y+d));
                    count++;
                  }
                }
              }
          }
          newRed = (short)(newRed/count);
          newGreen = (short)(newGreen/count);
          newBlue = (short)(newBlue/count);
          finalPI.setPixel(x,y,newRed,newGreen,newBlue);
        }
      }

      numIterations--;
      copyPI = finalPI;
    }
    return copyPI;

  }

  /**
   * mag2gray() maps an energy (squared vector magnitude) in the range
   * 0...24,969,600 to a grayscale intensity in the range 0...255.  The map
   * is logarithmic, but shifted so that values of 5,080 and below map to zero.
   *
   * DO NOT CHANGE THIS METHOD.  If you do, you will not be able to get the
   * correct images and pass the autograder.
   *
   * @param mag the energy (squared vector magnitude) of the pixel whose
   * intensity we want to compute.
   * @return the intensity of the output pixel.
   */
  private static short mag2gray(long mag) {
    short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

    // Make sure the returned intensity is in the range 0...255, regardless of
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
   * image.  The Sobel operator computes a magnitude that represents how
   * strong the edge is.  We compute separate gradients for the red, blue, and
   * green components at each pixel, then sum the squares of the three
   * gradients at each pixel.  We convert the squared magnitude at each pixel
   * into a grayscale pixel intensity in the range 0...255 with the logarithmic
   * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
   * pixel intensities reflect the strength of the edges.
   *
   * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
   *
   * @return a grayscale PixImage representing the edges of the input image.
   * Whiter pixels represent stronger edges.
   */

  public PixImage sobelEdges() {

    int temp_w = this.getWidth();
    int temp_h = this.getHeight();

    PixImage finalPI = new PixImage(temp_w, temp_h);

    // creat a new PixImage with the reflected pixels to take care of the edge cases  
    PixImage reflectPI = new PixImage(temp_w+2, temp_h+2);

    // set 4 corners of the reflectPI
    reflectPI.setPixel(0,0, this.getRed(0,0), getGreen(0,0), getBlue(0,0));
    reflectPI.setPixel(temp_w+1,0, this.getRed(temp_w-1,0), this.getGreen(temp_w-1,0), this.getBlue(temp_w-1,0));
    reflectPI.setPixel(0,temp_h+1, this.getRed(0,temp_h-1), this.getGreen(0,temp_h-1), this.getBlue(0,temp_h-1));
    reflectPI.setPixel(temp_w+1,temp_h+1, this.getRed(temp_w-1,temp_h-1), this.getGreen(temp_w-1,temp_h-1), getBlue(temp_w-1,temp_h-1));
  
    // Set the edge 2 rows of reflectPI

    for (int i=1; i<temp_w+1; i++) {
      reflectPI.setPixel(i, 0, this.getRed(i-1,0), this.getGreen(i-1,0), this.getBlue(i-1,0));
      reflectPI.setPixel(i, temp_h+1, this.getRed(i-1,temp_h-1), this.getGreen(i-1,temp_h-1), this.getBlue(i-1,temp_h-1));
    }

    // Set the edge 2 columns of reflectPI

    for (int j=1; j<temp_h+1; j++) {
      reflectPI.setPixel(0, j, this.getRed(0,j-1), this.getGreen(0,j-1), this.getBlue(0,j-1));
      reflectPI.setPixel(temp_w+1,j, this.getRed(temp_w-1,j-1), this.getGreen(temp_w-1,j-1), this.getBlue(temp_w-1,j-1));
    }

    // copy the entire array to middle of reflectPI
    for (int a=1; a<h+1; a++) {
      for (int b=1; b<w+1; b++) {
        reflectPI.setPixel(b,a,this.getRed(b-1,a-1), this.getGreen(b-1,a-1),this.getBlue(b-1,a-1));
      }
    }


    // Using reflectPI to assign avg values in newPI
    for (int y=0; y<temp_h; y++) { 
      for (int x=0; x<temp_w; x++) {
        // this[x][y] = reflectPI[x+1][y+1]

        long temp0x = (long)(reflectPI.getRed(x,y) + 2*reflectPI.getRed(x,y+1) + reflectPI.getRed(x,y+2)
          - reflectPI.getRed(x+2,y) - 2*reflectPI.getRed(x+2,y+1) - reflectPI.getRed(x+2,y+2));
        long temp0y = (long)(reflectPI.getRed(x,y) + 2*reflectPI.getRed(x+1,y) + reflectPI.getRed(x+2,y)
          - reflectPI.getRed(x,y+2) - 2*reflectPI.getRed(x+1,y+2) - reflectPI.getRed(x+2,y+2));

        long temp1x = (long)(reflectPI.getGreen(x,y) + 2*reflectPI.getGreen(x,y+1) + reflectPI.getGreen(x,y+2)
          - reflectPI.getGreen(x+2,y) - 2*reflectPI.getGreen(x+2,y+1) - reflectPI.getGreen(x+2,y+2));
        long temp1y = (long)(reflectPI.getGreen(x,y) + 2*reflectPI.getGreen(x+1,y) + reflectPI.getGreen(x+2,y)
          - reflectPI.getGreen(x,y+2) - 2*reflectPI.getGreen(x+1,y+2) - reflectPI.getGreen(x+2,y+2));

        long temp2x = (long)(reflectPI.getBlue(x,y) + 2*reflectPI.getBlue(x,y+1) + reflectPI.getBlue(x,y+2)
          - reflectPI.getBlue(x+2,y) - 2*reflectPI.getBlue(x+2,y+1) - reflectPI.getBlue(x+2,y+2));
        long temp2y = (long)(reflectPI.getBlue(x,y) + 2*reflectPI.getBlue(x+1,y) + reflectPI.getBlue(x+2,y)
          - reflectPI.getBlue(x,y+2) - 2*reflectPI.getBlue(x+1,y+2) - reflectPI.getBlue(x+2,y+2));

        long energy = (long)(Math.pow(temp0x,2)+Math.pow(temp0y,2) + Math.pow(temp1x,2)+Math.pow(temp1y,2)
          + Math.pow(temp2x,2)+Math.pow(temp2y,2));

        short intensity;
        intensity = mag2gray(energy);
        // Don't forget to use the method mag2gray() above to convert energies to pixel intensities.

        finalPI.setPixel(x,y,intensity,intensity,intensity);
      }
    }

    return finalPI;

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
   * equals() checks whether two images are the same, i.e. have the same
   * dimensions and pixels.
   *
   * @param image a PixImage to compare with "this" PixImage.
   * @return true if the specified PixImage is identical to "this" PixImage.
   */
  public boolean equals(PixImage image) {
    int width = getWidth();
    int height = getHeight();

    if (image == null ||
        width != image.getWidth() || height != image.getHeight()) {
      return false;
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (! (getRed(x, y) == image.getRed(x, y) &&
               getGreen(x, y) == image.getGreen(x, y) &&
               getBlue(x, y) == image.getBlue(x, y))) {
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
    PixImage image1 = array2PixImage(new int[][] { { 0, 10, 240 },
                                                   { 30, 120, 250 },
                                                   { 80, 250, 255 } });
    System.out.println("Testing getWidth/getHeight on a 3x3 image.  " +
                       "Input image:");
    System.out.print(image1);
    doTest(image1.getWidth() == 3 && image1.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 3x3 image.");
    doTest(image1.boxBlur(1).equals(
           array2PixImage(new int[][] { { 40, 108, 155 },
                                        { 81, 137, 187 },
                                        { 120, 164, 218 } })),
           "Incorrect box blur (1 rep):\n" + image1.boxBlur(1));
    doTest(image1.boxBlur(2).equals(
           array2PixImage(new int[][] { { 91, 118, 146 },
                                        { 108, 134, 161 },
                                        { 125, 151, 176 } })),
           "Incorrect box blur (2 rep):\n" + image1.boxBlur(2));
    doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
           "Incorrect box blur (1 rep + 1 rep):\n" +
           image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));

    System.out.println("Testing edge detection on a 3x3 image.");
    doTest(image1.sobelEdges().equals(
           array2PixImage(new int[][] { { 104, 189, 180 },
                                        { 160, 193, 157 },
                                        { 166, 178, 96 } })),
           "Incorrect Sobel:\n" + image1.sobelEdges());


    PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 },
                                                   { 0, 0, 100 } });
    System.out.println("Testing getWidth/getHeight on a 2x3 image.  " +
                       "Input image:");
    System.out.print(image2);
    doTest(image2.getWidth() == 2 && image2.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 2x3 image.");
    doTest(image2.boxBlur(1).equals(
           array2PixImage(new int[][] { { 25, 50, 75 },
                                        { 25, 50, 75 } })),
           "Incorrect box blur (1 rep):\n" + image2.boxBlur(1));

    System.out.println("Testing edge detection on a 2x3 image.");
    doTest(image2.sobelEdges().equals(
           array2PixImage(new int[][] { { 122, 143, 74 },
                                        { 74, 143, 122 } })),
           "Incorrect Sobel:\n" + image2.sobelEdges());
  }
}