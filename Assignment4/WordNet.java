/**
 * @author Dharshak
 * 
 * WordNet class and integrate Digraph class for adding edges 
 * and display the count of Vertices and Edges
 */

import java.util.*;
import java.io.*;
public class WordNet {

    private LinearProbingHashST<String, ArrayList<Integer>> htable;
    private LinearProbingHashST<Integer, String> htable1;
   
    int v;
    SAP sap;
    In in;
    Digraph d;

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) throws Exception {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("File name is null");
        }
        v = 0;
        readSynsets(synsets);
        readHypernyms(hypernyms);
    }

    private void readSynsets(String synsets) throws Exception {
        htable = new LinearProbingHashST<String, ArrayList<Integer>>();
        htable1 = new LinearProbingHashST<Integer, String>();

        Scanner scanSyn = new Scanner(new File(synsets));
        while (scanSyn.hasNextLine()) {
            v++;
            String[] str = scanSyn.nextLine().split(",");
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

    private void readHypernyms(String hypernyms) throws Exception {

        ArrayList<String> h_id = new ArrayList<String>();
        ArrayList<String[]> h_con = new ArrayList<String[]>();

        Scanner scanHyp = new Scanner(new File(hypernyms));
        while(scanHyp.hasNextLine()) {
            String[] str = scanHyp.nextLine().split(",",2);
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
   public static void main(String[] args)  throws Exception {
        String syn = "synsets.txt";
        String hyp = "hypernyms.txt";

        WordNet w = new WordNet(syn, hyp);
        // System.out.println(w.nouns());
        System.out.println(w.isNoun("Agamidae"));

        // System.out.println(sap.length(a, b));
        System.out.println("Pair 1: ");
        System.out.println(w.distance("1830s", "1840s"));
        System.out.println(w.sap("1830s", "1840s"));

        System.out.println("Pair 2: ");
        System.out.println(w.distance("Achras", "genus_Achras"));
        System.out.println(w.sap("Achras", "genus_Achras"));

        System.out.println("Pair 3: ");
        System.out.println(w.distance("Actinidia", "genus_Actinidia"));
        System.out.println(w.sap("Actinidia", "genus_Actinidia"));

        System.out.println("Pair 4: ");
        System.out.println(w.distance("Adams", "Robert_Adam"));
        System.out.println(w.sap("Adams", "Robert_Adam"));
    }
}