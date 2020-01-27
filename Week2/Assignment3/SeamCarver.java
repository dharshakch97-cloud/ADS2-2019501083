import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {

   int BORDER_ENERGY = 1000;
   int width;
   int height;
   Picture picture;
   private double[] distTo;
   private int[][] edgeTo;
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
       
   public int[] findVerticalSeam() {
        distTo = new double[width()];
        edgeTo = new int[width()][height()];

        for (int i = 0; i < distTo.length; i++) {
            distTo[i] = 1000;
        }

        for (int i = 1; i < height(); i++) {
            double[] lastDistTo = distTo.clone();
            for (int k = 0; k < distTo.length; k++) {
                distTo[k] = Double.POSITIVE_INFINITY;
            }
            for (int j = 1; j < width(); j++) {
                int x = j;
                int y = i;
                double energy = energy(x, y);
                v_relax(j - 1, x, y, energy, lastDistTo);
                v_relax(j, x, y, energy, lastDistTo);
                v_relax(j + 1, x, y, energy, lastDistTo);
            }
        }

        double minimumWeight = Double.POSITIVE_INFINITY;
        int minimum = 0;
        for (int i = 0; i < distTo.length; i++) {
            double weight = distTo[i];
            if (weight < minimumWeight) {
                minimumWeight = weight;
                minimum = i;
            }
        }

        int[] v_seam = new int[height()];
        for (int y = height() - 1; y >= 0; y--) {
            v_seam[y] = minimum;
            minimum = edgeTo[minimum][y];
        }
        return v_seam;
    }

    private void v_relax(int pre, int x, int y, double energy, double[] lastDistTo) {
        if (pre < 0 || pre >= lastDistTo.length) {
            return;
        }

        double weight = lastDistTo[pre];
        int index = x;
        if (distTo[index] > weight + energy) {
            distTo[index] = weight + energy;
            edgeTo[x][y] = pre;
        }
    }  
}
