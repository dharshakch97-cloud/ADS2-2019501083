import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

/**
 * Class for seam carver.
 */
public class SeamCarver {
    private static final int BORDER_ENERGY = 1000;
    private int width;
    private int height;
    private final Picture picture;
    //
    // create a seam carver object based on the given picture
    //
    // @param      picture  The picture
    //
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture is null");
        }
        this.width = picture.width();
        this.height = picture.height();
        this.picture = picture;
    }
    //
    // current picture
    //
    // @return     { description_of_the_return_value }
    //
    public Picture picture() {
        return picture;
    }
    //
    // width of current picture
    //
    // @return     { description_of_the_return_value }
    //
    public int width() {
        return width;
    }

    //
    // height of current picture
    //
    // @return     { description_of_the_return_value }
    //
    public int height() {
        return height;
    }

    //
    // energy of pixel at column x and row y
    //
    // @param      x     { parameter_description }
    // @param      y     { parameter_description }
    //
    // @return     { description_of_the_return_value }
    //
    public double energy(int x, int y) {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return BORDER_ENERGY;
        }

        int deltaX = calculateSquare(x + 1, y, x - 1, y);
        int deltaY = calculateSquare(x, y + 1, x, y - 1);
        return Math.sqrt(deltaX + deltaY);
    }

    /**
     * Calculates the square.
     *
     * @param      x1    The x 1
     * @param      y1    The y 1
     * @param      x2    The x 2
     * @param      y2    The y 2
     *
     * @return     The square.
     */
    private int calculateSquare(int x1, int y1, int x2, int y2) {
        Color color1 = picture.get(x1, y1);
        Color color2 = picture.get(x2, y2);
        int red = color1.getRed() - color2.getRed();
        int green = color1.getGreen() - color2.getGreen();
        int blue = color1.getBlue() - color2.getBlue();
        return red * red + green * green + blue * blue;
    }

    //
    // sequence of indices for horizontal seam
    //
    // @return     { description_of_the_return_value }
    //
    public int[] findHorizontalSeam() {
        double[][] sum = new double[width][height];
        int[][] parent = new int[width][height];
        for (int y = 0; y < height; ++y) {
            sum[0][y] = BORDER_ENERGY;
            parent[0][y] = y;
        }

        for (int x = 1; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                double temp = sum[x - 1][y];
                // parent[x][y] = y;
                if (y > 0 && sum[x - 1][y - 1] < temp) {
                    temp = sum[x - 1][y - 1];
                    // parent[x][y] = y - 1;
                }

                if (y < height - 1 && sum[x - 1][y + 1] < temp) {
                    temp = sum[x - 1][y + 1];
                    // parent[x][y] = y + 1;
                }
                sum[x][y] = energy(x, y) + temp;
            }
        }

        for (int x = 1; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                double temp = sum[x - 1][y];
                parent[x][y] = y;
                if (y > 0 && sum[x - 1][y - 1] < temp) {
                    temp = sum[x - 1][y - 1];
                    parent[x][y] = y - 1;
                }

                if (y < height - 1 && sum[x - 1][y + 1] < temp) {
                    temp = sum[x - 1][y + 1];
                    parent[x][y] = y + 1;
                }
            }
        }

        int index = 0;
        for (int y = 1; y < height; ++y) {
            if (sum[width - 1][y] < sum[width - 1][index]) {
                index = y;
            }
        }
        int[] seam = new int[width];
        seam[width - 1] = index;
        for (int x = width - 2; x >= 0; --x) {
            seam[x] = parent[x + 1][index];
            index = parent[x + 1][index];
        }
        return seam;
    }

    //
    // sequence of indices for vertical seam
    //
    // @return     { description_of_the_return_value }
    //
    public int[] findVerticalSeam() {
        double[][] sum = new double[width][height];
        int[][] parent = new int[width][height];
        for (int x = 0; x < width; ++x) {
            sum[x][0] = BORDER_ENERGY;
            parent[x][0] = x;
        }
        // adding pixel energy with least adjucent pixel
        for (int y = 1; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                double temp = sum[x][y - 1];
                // parent[x][y] = x;
                if (x > 0 && sum[x - 1][y - 1] < temp) {
                    temp = sum[x - 1][y - 1];
                    // parent[x][y] = x - 1;
                }

                if (x < width - 1 && sum[x + 1][y - 1] < temp) {
                    temp = sum[x + 1][y - 1];
                    // parent[x][y] = x + 1;
                }
                sum[x][y] = energy(x, y) + temp;
            }
        }
        // finding pixcels with least adjucent pixel
        for (int y = 1; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                double temp = sum[x][y - 1];
                parent[x][y] = x;
                if (x > 0 && sum[x - 1][y - 1] < temp) {
                    temp = sum[x - 1][y - 1];
                    parent[x][y] = x - 1;
                }

                if (x < width - 1 && sum[x + 1][y - 1] < temp) {
                    temp = sum[x + 1][y - 1];
                    parent[x][y] = x + 1;
                }
            }
        }
        int index = 0;
        for (int x = 1; x < width; ++x) {
            if (sum[x][height - 1] < sum[index][height - 1]) {
                index = x;
            }
        }
        int[] seam = new int[height];
        seam[height - 1] = index;
        for (int y = height - 2; y >= 0; --y) {
            seam[y] = parent[index][y + 1];
            index = parent[index][y + 1];
        }
        return seam;
    }

    //
    // remove horizontal seam from current picture
    //
    // @param      seam  The seam
    //
    public void removeHorizontalSeam(int[] seam) {

        if (seam == null || seam.length != width) {
            throw new IllegalArgumentException("Seam is illegal.");
        }

        if (height <= 1) {
            throw new IllegalArgumentException("Height of the picture is less than or equal to 1");
        }

        for (int x = 0; x < width; ++x) {
            for (int y = seam[x]; y < height - 1; ++y) {
                picture.set(x, y, picture.get(x, y + 1));
            }
        }

        --height;
    }

    //
    // remove vertical seam from current picture
    //
    // @param      seam  The seam
    //
    public void removeVerticalSeam(int[] seam) {

        if (seam == null || seam.length != height) {
            throw new IllegalArgumentException("Seam is illegal.");
        }

        if (width <= 1) {
            throw new IllegalArgumentException("Width of the picture is less than or equal to 1");
        }

        for (int y = 0; y < height; ++y) {
            for (int x = seam[y]; x < width - 1; ++x) {
                picture.set(x, y, picture.get(x + 1, y));
            }
        }

        --width;
    }
}