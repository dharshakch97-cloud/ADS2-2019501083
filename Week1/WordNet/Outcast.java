/** 
 * @author Dharshak
 *
 * This class Outcast used to find unrelated word in the
 * outcast*.txt files by calculating the maximum distances of those words 
 * in between them of those words
*/

public class Outcast {

   // variable declaration for WordNet
   private final WordNet wordnet;
	// constructor takes a WordNet object
   public Outcast(WordNet wordnet) {
   		this.wordnet = wordnet;
   } 

   /** 
    * @param nouns input nouns of type String[] 
    * 
    * input an array of WordNet nouns, 
    * returns an outcast
   */      

   public String outcast(String[] nouns) {
   		int d = 0;
   		String outcast = null;

   		for (String i : nouns) {
   			int distance = 0;
   			for (String j : nouns) {
   				int distLen = wordnet.distance(i, j);
   				distance += distLen;
   			}
   			if (distance > d) {
   				d = distance;
   				outcast = i;
   			}
   		}
   		return outcast;
   } 
   // see test client below
   // public static void main(String[] args)
}
