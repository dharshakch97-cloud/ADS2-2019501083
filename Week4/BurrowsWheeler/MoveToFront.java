import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] seq = createseq();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int i = 0;

            for (i = 0; i < seq.length; i++) {
                if (seq[i] == c) break;
            }

            BinaryStdOut.write((char) i);
            seq = moveToFront(seq, c, i);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] seq = createseq();

        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            char c = seq[i];
            BinaryStdOut.write(c);
            
            seq = moveToFront(seq, c, i);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    private static char[] createseq() {
        char[] seq = new char[R];
        for (int i = 0; i < R; i++) {
            seq[i] = (char) i;
        }

        return seq;
    }

    private static char[] moveToFront(char[] seq, char c, int i) {
        for (int j = i; j > 0; j--) {
            seq[j] = seq[j - 1];
        }

        seq[0] = c;

        return seq;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
        else
            throw new IllegalArgumentException();
    }
}
