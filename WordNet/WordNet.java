import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

/**
 * @author Dharshak
 * 
 * WordNet class and integrate Digraph class for adding edges 
 * and display the count of Vertices and Edges, reading the synsets and 
 * hypernets files and implementations for isNoun, distance, and sap methods
 */
public class WordNet {

    private LinearProbingHashST<String, ArrayList<Integer>> htable;
    private LinearProbingHashST<Integer, String> htable1;
   
    private int v;
    private SAP sap;
    private In in;
    private Digraph d;

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("File name is null");
        }
        v = 0;
        readSynsets(synsets);
        readHypernyms(hypernyms);
    }

    private void readSynsets(String synsets) {
        htable = new LinearProbingHashST<String, ArrayList<Integer>>();
        htable1 = new LinearProbingHashST<Integer, String>();

        In in = new In(synsets);
        while (in.hasNextLine()) {
            v++;
            String[] str = in.readLine().split(",");
            int id = Integer.parseInt(str[0]);
            htable1.put(id, str[1]);

            String[] words = str[1].split(" ");
            for (int i = 0; i < words.length; i++) {
                if (htable.contains(words[i])) {
                    ArrayList<Integer> id_list = htable.get(words[i]);
                    id_list.add(id);
                    htable.put(words[i], id_list);
                }
                else {
                    ArrayList<Integer> new_id = new ArrayList<Integer>();
                    new_id.add(id);
                    htable.put(words[i], new_id);
                }
            }
        }
        // System.out.println("Vertices: " + v);
        // System.out.println(htable1);
    }

    private void readHypernyms(String hypernyms) {

        ArrayList<String> h_id = new ArrayList<String>();
        ArrayList<String[]> h_con = new ArrayList<String[]>();

        In in = new In(hypernyms);
        while(in.hasNextLine()) {
            String[] str = in.readLine().split(",",2);
            if (str.length > 1) {
                h_id.add(str[0]);
                h_con.add(str[1].split(","));
            }
            else {
                h_id.add(str[0]);
                h_con.add(null);
            }
            // for (int i = 1; i < str.length; i++) {
            //     System.out.println(str[0] + " " + str[i]);
            //     d.addEdge(Integer.parseInt(str[0]), Integer.parseInt(str[i]));
            // }
        }

        int e = 0;
        d = new Digraph(h_id.size());
        for (int i = 0; i < h_id.size(); i++) {
            if (h_con.get(i) != null) {
                for (int j = 0; j < h_con.get(i).length; j++) {
                    int d1 = Integer.parseInt(h_id.get(i));
                    int d2 = Integer.parseInt(h_con.get(i)[j]);
                    d.addEdge(d1, d2);
                    e++;
                }
            }
        }

        DirectedCycle dc = new DirectedCycle(d);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException("Cycle detected");
        }
        int numRoot = 0;
        for (int i = 0; i < d.V(); ++i) {
            if (d.outdegree(i) == 0) {
                ++numRoot;
                if (numRoot > 1) {
                    throw new IllegalArgumentException("More than 1 root");
                }
            }
        }
        // System.out.println("Edges: " + e);
    }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
        return htable.keys();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word is null");
        }
        return htable.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
        sap = new SAP(d);
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not WordNet Noun");
        }
        return sap.length(htable.get(nounA), htable.get(nounB));
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
        sap = new SAP(d);
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not WordNet Noun");
        }  
        return htable1.get(sap.ancestor(htable.get(nounA), htable.get(nounB)));
   }
   // do unit testing of this class
   // public static void main(String[] args)  throws Exception {
 }