/**
 * @author Dharshak
 * 
 * WordNet class and integrate Digraph class for adding edges 
 * and display the count of Vertices and Edges
 */
// import java.io.*;
// import java.util.*;

// public class WordNet {

    // ArrayList for keys_hid of String type 
    // static ArrayList<String> key_hid = new ArrayList<String>();
    // ArrayList for values_hid of String array type
    // static ArrayList<String[]> values_hid = new ArrayList<String[]>();

    // private void parseHypernyms(String filename) throws FileNotFoundException, IOException {
    //     File  read_hypernyms = new File(filename); // importing the file "hypernyms.txt"
    //     // reading the file "hypernyms.txt" file
    //     BufferedReader br = new BufferedReader(new FileReader(read_hypernyms)); 
    //     String st;
    //     int v = 0;
    //     int d1, d2;
    //     while ((st = br.readLine()) != null) {
    //         String[] data = st.split(",",2);  // splitting the each line into two with "," seperated      
    //         v++;

    //         if (data.length > 1){
    //             key_hid.add(data[0]);
    //             values_hid.add(data[1].split(","));
    //         }
    //         else {
    //             key_hid.add(data[0]);
    //             values_hid.add(null);
    //         }
    //     }
    //     System.out.println("Vertices:" + v); // displaying the number of vertices
    // }

    // public static void main(String[] args) throws FileNotFoundException, IOException {
    //     WordNet w = new WordNet(); // object for WordNet to call method ;parseHypernyms'
    //     String hypernyms_file = "hypernyms.txt";
    //     w.parseHypernyms(hypernyms_file); // method call 'parseHypernyms'

    //     // Creating object for Digraph with parameter 'number of vertices'
    //     Digraph digraph = new Digraph(key_hid.size());
    //     int e = 0;
    //     for (int i = 0; i < key_hid.size(); i++) {
    //         if (values_hid.get(i) != null) {
    //             for (int j = 0; j < values_hid.get(i).length; j++) {
    //                 int d1 = Integer.parseInt(key_hid.get(i)); // converting of String to Integer type
    //                 // converting each value in String array to Integer type
    //                 int d2 = Integer.parseInt(values_hid.get(i)[j]);
    //                 digraph.addEdge(d1, d2); // adding the edge of two vertices
    //                 e++;
    //             }
    //         }
    //     }
    //     System.out.println("Edges:" + e); // displaying the number of edges
    //     // System.out.println(digraph.toString());
    //     System.out.println("No. of indegrees: " + digraph.indegree(34));
    //     System.out.println("No. of outdegrees: " + digraph.outdegree(34));

    //     SAP sap = new SAP(digraph);
    //     System.out.println(sap.length(100, 200));
    //     System.out.println(sap.ancestor(100, 200));

    //     In in = new In("digraph1.txt");
    //     Digraph dg = new Digraph(in);
    //     SAP sap_dg = new SAP(dg);

    //     System.out.println("length = " + sap_dg.length(3, 11) +  ", Ancestor = " + sap_dg.ancestor(3, 11));
    //     System.out.println("length = " + sap_dg.length(9, 12) +  ", Ancestor = " + sap_dg.ancestor(9, 12));
    //     System.out.println("length = " + sap_dg.length(7, 2) +  ", Ancestor = " + sap_dg.ancestor(7, 2));
    //     System.out.println("length = " + sap_dg.length(1, 6) +  ", Ancestor = " + sap_dg.ancestor(1, 6));
    // }
// }

import java.util.*;
import java.io.*;
public class WordNet {

    Map<Integer, String[]> id_noun;
    Map<String, Integer> noun_id; 
    // static ArrayList<Integer> key_sId = new ArrayList<Integer>();
    // static ArrayList<String[]> name_sId = new ArrayList<Integer>();

    // static ArrayList<String> key_hId = new ArrayList<String>();
    // static ArrayList<String[]> values_hId = new ArrayList<String[]>();
    int V;
    SAP sap;
    In in;

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("File name is null");
        }
        Map<Integer, String[]> id_noun = new HashMap<Integer, String>();
        Map<Integer, String> id_id2 = new HashMap<Integer, String>();
        int V = 0;
        readSynsets(synsets);
        readHypernyms(hypernyms);
    }

    private void readSynsets(String synsets) {
        
    }

    private void readHypernyms(String hypernyms) {
        
    }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
        return noun_Id.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word is null");
        }
        return noun_Id.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not WordNet Noun");
        }
        return sap.length(noun_Id.get(nounA), noun_Id.get(nounB));
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not WordNet Noun");
        }  
        return id_noun.get(sap.ancestor(noun_Id.get(nounA), noun_Id.get(nounB)));
   }

   // do unit testing of this class
   public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph dg = new Digraph(in);
        SAP sap_dg = new SAP(dg);

        System.out.println("length = " + sap_dg.length(3, 11) +  ", Ancestor = " + sap_dg.ancestor(3, 11));
        System.out.println("length = " + sap_dg.length(9, 12) +  ", Ancestor = " + sap_dg.ancestor(9, 12));
        System.out.println("length = " + sap_dg.length(7, 2) +  ", Ancestor = " + sap_dg.ancestor(7, 2));
        System.out.println("length = " + sap_dg.length(1, 6) +  ", Ancestor = " + sap_dg.ancestor(1, 6));

        String syn = "synsets.txt";
        String hyp = "hypernyms.txt";

        WordNet w = new WordNet(syn, hyp);
        System.out.println(w.nouns());
    }
}