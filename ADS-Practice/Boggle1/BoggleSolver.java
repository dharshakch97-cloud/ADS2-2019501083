import java.util.HashMap;
public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private TrieSET listOfWords;
    private HashMap<String, Integer> validWords;
    public BoggleSolver(String[] dictionary) {
        listOfWords = new TrieSET();
        for (String word : dictionary) {
            listOfWords.add(word);
        }
    }
    // Returns the map of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new NullPointerException("board is null");
        }
        validWords = new HashMap<String, Integer>();

        int row = board.rows();
        int col = board.cols();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boolean[][] visited = new boolean[row][col];
                collect(board, i, j, visited, "", validWords);
            }
        }
        return validWords.keySet();
    }
    private void collect(BoggleBoard board, int row, int col, boolean[][] visited, String prefix, HashMap<String, Integer> map) {
        if (visited[row][col]) {
            return;
        }

        char letter = board.getLetter(row, col);
        String word = prefix;

        if (letter == 'Q') {
            word += "QU";
        } else {
            word += letter;
        }

        if (!listOfWords.hasPrefix(word)) {
            return;
        }

        if (word.length() > 2 && listOfWords.contains(word)) {
            if (word.length() > 2 && word.length() < 5)
                map.put(word, 1);
            if (word.length() == 5)
                map.put(word, 2);
            if (word.length() == 6)
                map.put(word, 3);
            if (word.length() == 7)
                map.put(word, 5);
            if (word.length() >= 8)
                map.put(word, 11);

        }

        visited[row][col] = true;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if ((row + i >= 0) && (row + i < board.rows()) && (col + j >= 0) && (col + j < board.cols())) {
                    collect(board, row + i, col + j, visited, word, map);
                }
            }
        }

        visited[row][col] = false;

    }
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (listOfWords.contains(word)) {
            return validWords.get(word);
        }
        return 0;
    }
}