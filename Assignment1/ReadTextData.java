  
import java.io.*;
import java.util.*;

class ReadTextData {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ReadTextData r = new ReadTextData();
        String synsets_file = "synsets.txt";
        String hypernyms_file = "hypernyms.txt";
        r.parseSynsets(synsets_file);
        System.out.println();
        r.parseHypernyms(hypernyms_file);
    } 

    private void parseSynsets(String filename) throws FileNotFoundException, IOException {
        File  read_synsets = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(read_synsets)); 
        String st;
        int i = 0;
        while ((st = br.readLine()) != null) {
            if (i == 5) break;
            String[] data = st.split(",");
            i++;
            for (int j = 0; j < data.length; j++)
                System.out.println(data[j]);
        } 
    }

    private void parseHypernyms(String filename) throws FileNotFoundException, IOException {
        File  read_hypernyms = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(read_hypernyms)); 
        String st;
        int i = 0;
        while ((st = br.readLine()) != null) {
            if (i == 5) break;
            String[] data = st.split(",",2);
            i++;
            for (int j = 0; j < data.length; j++)
                System.out.println(data[j]);
        } 
    }
}