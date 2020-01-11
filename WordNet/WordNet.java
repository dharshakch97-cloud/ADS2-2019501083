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

    private LinearProbingHashST<String, ArrayList<Integer>> nounIds;
    private LinearProbingHashST<Integer, String> idNoun;
   
    private SAP sap;
    private Digraph d;

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("File name is null");
        }
        readSynsets(synsets);
        readHypernyms(hypernyms);
    }

    private void readSynsets(String synsets) {
        nounIds = new LinearProbingHashST<String, ArrayList<Integer>>();
        idNoun = new LinearProbingHashST<Integer, String>();

        In inSyn = new In(synsets);
        while (inSyn.hasNextLine()) {
            String[] str = inSyn.readLine().split(",");
            int id = Integer.parseInt(str[0]);
            idNoun.put(id, str[1]);

            String[] words = str[1].split(" ");
            for (int i = 0; i < words.length; i++) {
                if (nounIds.contains(words[i])) {
                    ArrayList<Integer> idList = nounIds.get(words[i]);
                    idList.add(id);
                    nounIds.put(words[i], idList);
                }
                else {
                    ArrayList<Integer> newId = new ArrayList<Integer>();
                    newId.add(id);
                    nounIds.put(words[i], newId);
                }
            }
        }
        // System.out.println("Vertices: " + v);
        // System.out.println(htable1);
    }

    private void readHypernyms(String hypernyms) {

        ArrayList<String> hId = new ArrayList<String>();
        ArrayList<String[]> hCon = new ArrayList<String[]>();

        In inHyp = new In(hypernyms);
        while (inHyp.hasNextLine()) {
            String[] str = inHyp.readLine().split(",", 2);
            if (str.length > 1) {
                hId.add(str[0]);
                hCon.add(str[1].split(","));
            }
            else {
                hId.add(str[0]);
                hCon.add(null);
            }
            // for (int i = 1; i < str.length; i++) {
            //     System.out.println(str[0] + " " + str[i]);
            //     d.addEdge(Integer.parseInt(str[0]), Integer.parseInt(str[i]));
            // }
        }

        d = new Digraph(hId.size());
        for (int i = 0; i < hId.size(); i++) {
            if (hCon.get(i) != null) {
                for (int j = 0; j < hCon.get(i).length; j++) {
                    int d1 = Integer.parseInt(hId.get(i));
                    int d2 = Integer.parseInt(hCon.get(i)[j]);
                    d.addEdge(d1, d2);
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
        return nounIds.keys();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word is null");
        }
        return nounIds.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
        sap = new SAP(d);
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not WordNet Noun");
        }
        return sap.length(nounIds.get(nounA), nounIds.get(nounB));
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
        sap = new SAP(d);
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not WordNet Noun");
        }  
        return idNoun.get(sap.ancestor(nounIds.get(nounA), nounIds.get(nounB)));
   }
   // do unit testing of this class
   // public static void main(String[] args)  throws Exception {
 }