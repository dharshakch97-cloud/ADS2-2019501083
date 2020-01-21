// importing required class files from edu.princeton.cs.algs4

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

    // Hashtable for Noun - Ids 
    private LinearProbingHashST<String, ArrayList<Integer>> nounIds;
    // Hashtable for Id - Noun
    private LinearProbingHashST<Integer, String> idNoun;
    // SAP class object
    private SAP sap;
    // Digraph class object
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

        // Reading synsets file and storing the data in the HashTable
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
    }

    private void readHypernyms(String hypernyms) {

        ArrayList<String> hId = new ArrayList<String>();
        ArrayList<String[]> hCon = new ArrayList<String[]>();

        // Reading hypernyms file and storing the data in the ArrayLists
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
        }

        // Creating the digraph and adding edges to all the vertices 
        d = new Digraph(hId.size());
        for (int i = 0; i < hId.size(); i++) {
            if (hCon.get(i) != null) {
                for (int j = 0; j < hCon.get(i).length; j++) {
                    int d1 = Integer.parseInt(hId.get(i));
                    int d2 = Integer.parseInt(hCon.get(i)[j]);
                    d.addEdge(d1, d2); // adding the edges
                }
            }
        }

        // Checking if the graph constructed is directed cycle or not
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
    }

    /** 
     * returns all WordNet nouns
     *
    */

    public Iterable<String> nouns() {
        return nounIds.keys();
    }

    /** 
     * @param word input word of type String
     *
     * checks if the input word a WordNet noun or not?
    */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word is null");
        }
        return nounIds.contains(word);
    }

    /** 
     * @param nounA input nounA of type String
     * @param nounB input nounB of type String
     *
     * calcates the distance between two input nouns in the WordNet graph
     * nounA and nounB
    */

    public int distance(String nounA, String nounB) {
        sap = new SAP(d);
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not WordNet Noun");
        }
        return sap.length(nounIds.get(nounA), nounIds.get(nounB));
    }

    /** 
     * @param nounA input nounA of type String
     * @param nounB input nounB of type String
     *
     * To find the common ancestor in the paths between nounA and nounB
     * paths which path is the shortest ancestral path
    */

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
