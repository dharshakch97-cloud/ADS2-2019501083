import java.util.Set;
import java.util.HashSet;

public class BoggleSolver
{
	private TrieSET ts;
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
    Set<String> words = new HashSet<String>();
    if (board == null) {
      throw new NullPointerException("board is null");
    }
    for (int i = 0; i < board.rows(); i++) {
      for (int j = 0; j < board.cols(); j++) {
        boolean[][] checked = new boolean[board.rows()][board.cols()];
        check_TrieWord(board, i, j, checked, "", words);
      }
    }
    return words;
  }

  private void check_TrieWord(BoggleBoard board, int r, int c, boolean[][] isChecked, String prefix, Set<String> words) {
    if (isChecked[r][c]) {
      return;
    }

    char letter = board.getLetter(r, c);
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
      words.add(word);
    }

    isChecked[r][c] = true;

    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) {
          continue;
        }

        if ((r + i >= 0) && (r + i < board.rows()) && (c + j >= 0) && (c + j < board.cols())) {
          check_TrieWord(board, r + i, c + j, isChecked, word, words);
        }
      }
    }

    isChecked[r][c] = false;
  }
  
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (ts.contains(word)) {
            switch(word.length()) {
                case 0 :
                case 1 :
                case 2 : return 0;
                case 3 :
                case 4 : return 1;
                case 5 : return 2;
                case 6 : return 3;
                case 7 : return 5;
                default : return 11;
            }
        }
        return 0;  
    }
}
