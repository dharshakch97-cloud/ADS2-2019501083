import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
  
   int width;
   int height;
   Picture picture;
   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture is null");
        }

        width = picture.width();
        height = picture.height();
        this.picture = picture;
   }

   // current picture
   public Picture picture() {
        return picture;
   }

   // width of current picture
   public int width() {
        return width;
   }

     // height of current picture
     public int height() {
          return height;
     }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if(x < 0 || x >= width() || y < 0 || y >= height())
            throw new IndexOutOfBoundsException();

        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }

        int delta_X = delta_energyCalculate(x + 1, y, x - 1, y);
        int delta_Y = delta_energyCalculate(x, y + 1, x, y - 1);

        double energy = Math.sqrt(delta_X + delta_Y);
        return energy;
   }

     private int delta_energyCalculate(int x1, int y1, int x2, int y2) {
        Color c1 = picture.get(x1, y1);
        Color c2 = picture.get(x2, y2);

        int r = c1.getRed() - c2.getRed();
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();

        int rgb = (r * r) + (g * g) + (b * b);
        return rgb;
   }
}
