/******************************************************************************
 *  Compilation:  javac BoggleBoard.java
 *  Execution:    java  BoggleBoard
 *  Dependencies: StdRandom.java In.java System.out.java
 *
 *  A data type for Boggle boards.
 *
 ******************************************************************************/

public class BoggleBoard {
    // the 16 Boggle dice (1992 version)
    private static final String[] BOGGLE_1992 = {
        "LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS",
        "ANAEEG", "IDSYTT", "OATTOW", "MTOICU",
        "AFPKFS", "XLDERI", "HCPOAS", "ENSIEU",
        "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"
    };

    // the 16 Boggle dice (1983 version)
    private static final String[] BOGGLE_1983 = {
        "AACIOT", "ABILTY", "ABJMOQ", "ACDEMP",
        "ACELRS", "ADENVZ", "AHMORS", "BIFORX",
        "DENOSW", "DKNOTU", "EEFHIY", "EGINTV",
        "EGKLUY", "EHINPS", "ELPSTU", "GILRUW",
    };

    // the 25 Boggle Master / Boggle Deluxe dice
    private static final String[] BOGGLE_MASTER = {
        "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
        "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCNSTW",
        "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DHHLOR",
        "DHHNOT", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
        "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"
    };

    // the 25 Big Boggle dice
    private static final String[] BOGGLE_BIG = {
        "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
        "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCENST",
        "CEIILT", "CEILPT", "CEIPST", "DDHNOT", "DHHLOR",
        "DHLNOR", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
        "FIPRSY", "GORRVW", "IPRRRY", "NOOTUW", "OOOTTU"
    };


    // letters and frequencies of letters in the English alphabet
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[] FREQUENCIES = {
        0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228,
        0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
        0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
        0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
        0.01974, 0.00074
    };

    private final int m;        // number of rows
    private final int n;        // number of columns
    private char[][] board;     // the m-by-n array of characters

    /**
     * Initializes a random 4-by-4 board, by rolling the Hasbro dice.
     */
    public BoggleBoard() {
        m = 4;
        n = 4;
        StdRandom.shuffle(BOGGLE_1992);
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String letters = BOGGLE_1992[n*i+j];
                int r = StdRandom.uniform(letters.length());
                board[i][j] = letters.charAt(r);
            }
        }
    }
    
    /**
     * Initializes a board from the given filename.
     * @param filename the name of the file containing the Boggle board
     */
    public BoggleBoard(String filename) {
        In in = new In(filename);
        m = in.readInt();
        n = in.readInt();
        if (m <= 0) throw new IllegalArgumentException("number of rows must be a positive integer");
        if (n <= 0) throw new IllegalArgumentException("number of columns must be a positive integer");
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String letter = in.readString().toUpperCase();
                if (letter.equals("QU"))
                    board[i][j] = 'Q';
                else if (letter.length() != 1)
                    throw new IllegalArgumentException("invalid character: " + letter);
                else if (!ALPHABET.contains(letter))
                    throw new IllegalArgumentException("invalid character: " + letter);
                else 
                    board[i][j] = letter.charAt(0);
            }
        }
    }

    /**
     * Initializes a random m-by-n board, according to the frequency
     * of letters in the English language.
     * @param m the number of rows
     * @param n the number of columns
     */
    public BoggleBoard(int m, int n) {
        this.m = m;
        this.n = n;
        if (m <= 0) throw new IllegalArgumentException("number of rows must be a positive integer");
        if (n <= 0) throw new IllegalArgumentException("number of columns must be a positive integer");
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int r = StdRandom.discrete(FREQUENCIES);
                board[i][j] = ALPHABET.charAt(r);
            }
        }
    }

    /**
     * Initializes a board from the given 2d character array,
     * with 'Q' representing the two-letter sequence "Qu".
     * @param a the 2d character array
     */
    public BoggleBoard(char[][] a) {
        this.m = a.length;
        if (m == 0) throw new IllegalArgumentException("number of rows must be a positive integer");
        this.n = a[0].length;
        if (n == 0) throw new IllegalArgumentException("number of columns must be a positive integer");
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            if (a[i].length != n)
                throw new IllegalArgumentException("char[][] array is ragged");
            for (int j = 0; j < n; j++) {
                if (ALPHABET.indexOf(a[i][j]) == -1)
                    throw new IllegalArgumentException("invalid character: " + a[i][j]);
                board[i][j] = a[i][j];
            }
        }
    }

    /**
     * Returns the number of rows.
     * @return number of rows
     */
    public int rows() {
        return m;
    }

    /**
     * Returns the number of columns.
     * @return number of columns
     */
    public int cols() {
        return n;
    }

    /**
     * Returns the letter in row i and column j,
     * with 'Q' representing the two-letter sequence "Qu".
     * @param i the row
     * @param j the column
     * @return the letter in row i and column j
     *    with 'Q' representing the two-letter sequence "Qu".
     */
    public char getLetter(int i, int j) {
        return board[i][j];
    }

    /**
     * Returns a string representation of the board, replacing 'Q' with "Qu".
     * @return a string representation of the board, replacing 'Q' with "Qu"
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(m + " " + n + "\n");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]);
                if (board[i][j] == 'Q') sb.append("u ");
                else sb.append("  ");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Unit tests the BoggleBoard data type.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        // BoggleBoard board = new BoggleBoard(args[1]);
        // int score = 0;
        // for (String word : solver.getAllValidWords(board)) {
        //     System.out.println(word);
        //     score += solver.scoreOf(word);
        // }
        // System.out.println("Score = " + score);
        for (String word : dictionary) {
            System.out.println(word);
        }
    }
}