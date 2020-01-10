public class Outcast {
	private WordNet wordnet;
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
   				int dist_len = wordnet.distance(i, j);
   				// System.out.println("distance (" + i + "," + j + ") :" + dist_len);
   				distance += dist_len;
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
   public static void main(String[] args) throws Exception {
   		WordNet wordnet = new WordNet(args[0], args[1]);
    	Outcast outcast = new Outcast(wordnet);
    	for (int t = 2; t < args.length; t++) {
        	In in = new In(args[t]);
        	String[] nouns = in.readAllStrings();
        	System.out.println(args[t] + ": " + outcast.outcast(nouns));
    	}
   	}
}
