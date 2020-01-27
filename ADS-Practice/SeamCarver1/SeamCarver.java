import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {

    private Picture picture;
    private double[] distTo;
    private int[][] edgeTo;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(this.picture);
    }             

    public int width() {
        return this.picture.width();
    }

    public int height() {
        return this.picture.height();
    }

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

    public int[] findHorizontalSeam() {
        distTo = new double[this.height()];
        edgeTo = new int[this.width()][this.height()];

        for (int i = 0; i < this.distTo.length; i++) {
            this.distTo[i] = 1000;
        }

        for (int i = 1; i < width(); i++) {
            double[] lastDistTo = this.distTo.clone();
            for (int k = 0; k < this.distTo.length; k++) {
                this.distTo[k] = Double.POSITIVE_INFINITY;
            }
            for (int j = 1; j < height(); j++) {
                int x = i;
                int y = j;
                double energy = energy(x, y);
                h_relax(j - 1, x, y, energy, lastDistTo);
                h_relax(j, x, y, energy, lastDistTo);
                h_relax(j + 1, x, y, energy, lastDistTo);
            }
        }

        double minWeight = Double.POSITIVE_INFINITY;
        int min = 0;
        for (int i = 0; i < this.distTo.length; i++) {
            double weight = this.distTo[i];
            if (weight < minWeight) {
                min = i;
                minWeight = weight;
            }
        }

        int[] hseam = new int[this.width()];
        for (int x = this.width() - 1; x >= 0; x--) {
            hseam[x] = min;
            min = edgeTo[x][min];
        }
        return hseam;
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

    private void h_relax(int pre, int x, int y, double energy, double[] lastDistTo) {
        if (pre < 0 || pre >= lastDistTo.length) {
            return;
        }
        double weight = lastDistTo[pre];
        int index = y;
        if (this.distTo[index] > weight + energy) {
            this.distTo[index] = weight + energy;
            this.edgeTo[x][y] = pre;
        }
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

    // 
    // public void removeHorizontalSeam(int[] seam) {
    //     if (seam == null || this.height() <= 1 || seam.length != this.width()) {
    //         throw new IllegalArgumentException();
    //     }
    //     Picture newPicture = new Picture(this.width(), this.height() - 1);
    //     int prevSeam = seam[0];
    //     for (int x = 0; x < this.width(); x++) {
    //         prevSeam = seam[x];
    //         for (int y = 0; y < this.height(); y++) {
    //             if (seam[x] == y) continue;
    //             Color color = this.picture.get(x, y);
    //             newPicture.set(x, seam[x] > y ? y : y - 1, color);
    //         }
    //     }
    //     this.picture = newPicture;
    // }
    // public void removeVerticalSeam(int[] seam) {
    //     if (seam == null || this.width() <= 1 || seam.length != this.height()) {
    //         throw new IllegalArgumentException();
    //     }
    //     Picture newPicture = new Picture(this.width() - 1, this.height());
    //     int prevSeam = seam[0];
    //     for (int y = 0; y < this.height(); y++) {
    //         prevSeam = seam[y];
    //         for (int x = 0; x < this.width(); x++) {
    //             Color color = this.picture.get(x, y);
    //             newPicture.set(seam[y] > x ? x : x - 1, y, color);
    //         }
    //     }
    //     this.picture = newPicture;
    // }