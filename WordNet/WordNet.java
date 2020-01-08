import java.io.*;
import java.util.*;

public class WordNet {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        WordNet w = new WordNet();
        String synsets_file = "synsets.txt";
        String hypernyms_file = "hypernyms.txt";
        Map<String, String> synsets_hm = w.parseSynsets(synsets_file);
        System.out.println();
        Map<String, String> hypersets_hm = w.parseHypernyms(hypernyms_file);
    } 

    private Map parseSynsets(String filename) throws FileNotFoundException, IOException {
        File  read_synsets = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(read_synsets)); 
        Map<String, String> synsets_hashmap = new HashMap<>();
        String st;
        int w_count = 0;
        while ((st = br.readLine()) != null) {
            String[] data = st.split(",");
            synsets_hashmap.put(data[0], data[1]);
            w_count++;
        } 
        // System.out.println(synsets_hashmap);
        System.out.println(w_count);
        return synsets_hashmap;
    }

    private Map parseHypernyms(String filename) throws FileNotFoundException, IOException {
        File  read_hypernyms = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(read_hypernyms)); 
        Map<String, String> hypernyms_hashmap = new HashMap<>();
        String st;
        int w_count = 0;
        while ((st = br.readLine()) != null) {
            String[] data = st.split(",");            
            w_count++;
            for (int j = 0; j < data.length; j+= 2)
                for(int k = 1; k < data.length; k+= 2)
                hypernyms_hashmap.put(data[j], data[k]);
                
        } 
        // System.out.println(hypernyms_hashmap);
        System.out.println(w_count);
        return hypernyms_hashmap;
    }
}