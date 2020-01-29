import java.util.Set;
import java.util.HashSet;

public class BoggleSolver
{
	TrieSET ts;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
    	ts = new TrieSET();
        for (String i : dictionary) {
            ts.add(i);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board) {
    Set<String> validWords = new HashSet<String>();
    if (board == null) {
      throw new NullPointerException("board is null");
    }
    for (int i = 0; i < board.rows(); i++) {
      for (int j = 0; j < board.cols(); j++) {
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        collect(board, i, j, marked, "", validWords);
      }
    }

    return validWords;
  }

  private void collect(BoggleBoard board, int row, int col, boolean[][] marked, String prefix, Set<String> set) {
    if (marked[row][col]) {
      return;
    }

    char letter = board.getLetter(row, col);
    String word = prefix;

    if (letter == 'Q') {
      word += "QU";
    } else {
      word += letter;
    }

    if (!ts.hasPrefix(word)) {
      return;
    }

    if (word.length() > 2 && ts.contains(word)) {
      set.add(word);
    }

    marked[row][col] = true;

    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) {
          continue;
        }

        if ((row + i >= 0) && (row + i < board.rows()) && (col + j >= 0) && (col + j < board.cols())) {
          collect(board, row + i, col + j, marked, word, set);
        }
      }
    }

    marked[row][col] = false;
  }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    // public Iterable<String> getAllValidWords(BoggleBoard board) {
    //     Set<String> words = new HashSet<String>();
    //     if (board == null)
    //         throw new IllegalArgumentException();

    //     for (int i = 0; i < board.rows(); i++) {
    //         for (int j = 0; j < board.cols(); j++) {
    //             boolean[][] checked = new boolean[board.rows()][board.cols()];
    //             check_trieWord(board, i, j, checked, "", words);
    //         }
    //     }
    //     for (String i : words)
    //         System.out.println(i);
    //     return words;
    // }

    // private void check_trieWord(BoggleBoard b, int r, int c, boolean[][] isChecked, String prf, Set<String> words) {
    //     if (isChecked[r][c])
    //         return;

    //     char letter = b.getLetter(r, c);
    //     if (letter == 'Q')
    //         prf += "QU";
    //     else
    //         prf += letter;

    //     if (!ts.hasPrefix(prf))
    //         return;

    //     if (prf.length() > 2 && ts.contains(prf))
    //         words.add(prf);

    //     isChecked[r][c] = true;

    //     for (int i = -1; i <= 1; i++) {
    //         for (int j = -1; j <= -1; j++) {
    //             if (i == 0 && j == 0)
    //                 continue;
    //             if ((r + i >= 0) && (r + i < b.rows()) && (c + i >= 0) && (c + i < b.cols()))
    //                 check_trieWord(b, r + i, c + i, isChecked, prf, words);
    //         }
    //     }

    //     isChecked[r][c] = false;
    // }
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (ts.contains(word)) {
            if (word.length() < 3)
                return 0;
            else if (word.length() >= 3 || word.length() < 4)
                return 1;
            else if (word.length() == 5)
                return 2;
            else if (word.length() == 6)
                return 3;
            else if (word.length() == 7)
                return 5;
            else if (word.length() >= 8)
                return 11;
        }
        return 0;  
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            System.out.println(word);
            score += solver.scoreOf(word);
        }
        System.out.println("Score = " + score);
    }
}
