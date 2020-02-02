import java.util.ArrayList;
public class BoggleSolver {
	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
	private TrieST<Integer> trie;
	private ArrayList<String> list;
	/**
	 *time complexity is O(dictionary length)
	 *
	 * @param      dictionary  The dictionary
	 */
	public BoggleSolver(String[] dictionary) {
		trie = new TrieST<Integer>();
		int[] scores = {0, 0, 0, 1, 1, 2, 3, 5, 11};
		list = new ArrayList<String>();
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i].length() > 8) {
				trie.put(dictionary[i], 11);
			} else {
				trie.put(dictionary[i], scores[dictionary[i].length()]);
			}
		}
	}

	/**
	 *time complexity is O(L)
	 *L is the length of string
	 * @param      word  The word
	 *
	 * @return     True if valid, False otherwise.
	 */
	private boolean isValid(String word) {
		if (!trie.hasPrefix(word)) {
			return false;
		}
		return true;
	}
	private String check(char ch) {
		if (ch == 'Q') {
			return "QU";
		}
		return ch + "";
	}
	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	//time complexity is O(rows*cols)
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		boolean[][] visited;
		for (int i = 0; i < board.rows(); i++) {
			for (int j = 0; j < board.cols(); j++) {
				visited = new boolean[board.rows()][board.cols()];
				dfs(board, i, j, check(board.getLetter(i, j)), visited);
			}
		}
		return list;
	}
	private boolean index(int i, int j, int rows, int cols) {
		if (i < 0 || i >= rows || j < 0 || j >= cols) {
			return false;
		}
		return true;
	}
	/**
	 *time complexity is O(vertices + edges)
	 *
	 * @param      board    The board
	 * @param      row      The row
	 * @param      col      The col
	 * @param      word     The word
	 * @param      visited  The visited
	 */
	private void dfs(BoggleBoard board, int row, int col, String word, boolean[][] visited) {
		int rows = board.rows();
		int cols = board.cols();
		if (row < 0 || col < 0 || row >= rows || col >= cols) {
			return;
		}
		visited[row][col] = true;
		if (trie.contains(word) && (!list.contains(word)) && word.length() > 2 && word != null) {
			list.add(word);
		}
		if (index(row + 1, col + 1, rows, cols) && !visited[row + 1][col + 1] && isValid(word)) {
			String s = word + check(board.getLetter(row + 1, col + 1));
			dfs(board, row + 1, col + 1, s, visited);
			visited[row + 1][col + 1] = false;
		}
		if (index(row - 1, col - 1, rows, cols) && !visited[row - 1][col - 1] && isValid(word)) {
			String s = word + check(board.getLetter(row - 1, col - 1));
			dfs(board, row - 1, col - 1, s , visited);
			visited[row - 1][col - 1] = false;
		}
		if (index(row - 1, col + 1, rows, cols) && !visited[row - 1][col + 1] && isValid(word)) {
			String s = word + check(board.getLetter(row - 1, col + 1));
			dfs(board, row - 1, col + 1, s, visited);
			visited[row - 1][col + 1] = false;
		}
		if (index(row + 1, col - 1, rows, cols) && !visited[row + 1][col - 1] && isValid(word)) {
			String s = word + check(board.getLetter(row + 1, col - 1));
			dfs(board, row + 1, col - 1, s, visited);
			visited[row + 1][col - 1] = false;
		}
		if (index(row - 1, col, rows, cols) && !visited[row - 1][col] && isValid(word)) {
			String s = word + check(board.getLetter(row - 1, col));
			dfs(board, row - 1, col, s, visited);
			visited[row - 1][col] = false;
		}
		if (index(row + 1, col, rows, cols) && !visited[row + 1][col] && isValid(word)) {
			String s = word + check(board.getLetter(row + 1, col));
			dfs(board, row + 1, col, s, visited);
			visited[row + 1][col] = false;
		}
		if (index(row, col + 1, rows, cols) && !visited[row][col + 1] && isValid(word)) {
			String s = word + check(board.getLetter(row, col + 1));
			dfs(board, row, col + 1, s, visited);
			visited[row][col + 1] = false;
		}
		if (index(row, col - 1, rows, cols) && !visited[row][col - 1] && isValid(word)) {
			String s = word + check(board.getLetter(row, col - 1));
			dfs(board, row, col - 1, s, visited);
			visited[row][col - 1] = false;
		}
	}
	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A through Z.)
	// time complexity is O(L)
	public int scoreOf(String word) {
		if (word == null) {
			return 0;
		}
		Integer val = trie.get(word);
		if (val == null) {
			return 0;
		}
		return val;
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
