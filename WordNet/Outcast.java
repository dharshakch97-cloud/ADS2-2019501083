/** 
 * @author Dharshak
 *
 * This class Outcast used to find unrelated word in the
 * outcast*.txt files by calculating the maximum distances of those words 
 * in between them of those words
*/
public class Outcast {
    private final WordNet wordnet;
	// constructor takes a WordNet object
   public Outcast(WordNet wordnet) {
   		this.wordnet = wordnet;
   } 
   // given an array of WordNet nouns, return an outcast      
   public String outcast(String[] nouns) {
   		int d = 0;
   		String outcast = null;

   		for (String i : nouns) {
   			int distance = 0;
   			for (String j : nouns) {
   				int distLen = wordnet.distance(i, j);
   				// System.out.println("distance (" + i + "," + j + ") :" + dist_len);
   				distance += distLen;
   			}
   			// System.out.println("max. distance: " + distance);
   			if (distance > d) {
   				d = distance;
   				outcast = i;
   			}
   			// System.out.println("distance: " + d + "outcast: " + outcast);
   		}
   		return outcast;
   } 
   // see test client below
   // public static void main(String[] args)
}
