/**
 * @author Dharshak
 * 
 * Assignment 1 Reading text data(CSV files)
 */  
import java.io.*;
import java.util.*;

class ReadTextData {

    /**
     * Main method for reading text data
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ReadTextData r = new ReadTextData(); // creating object for class ReadTextData
        String synsets_file = "synsets.txt";
        String hypernyms_file = "hypernyms.txt";
        r.parseSynsets(synsets_file);
        System.out.println();
        r.parseHypernyms(hypernyms_file);
    } 

    /**
     * parseSynsets method to read the synsets.txt file and
     * displaying in the console (or terminal).
     * 
     * @param filename string that contains filename with extension
     */
    private void parseSynsets(String filename) throws FileNotFoundException, IOException {
        File  read_synsets = new File(filename); // loads the file
        BufferedReader br = new BufferedReader(new FileReader(read_synsets)); // reads the file
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

    /**
     * parseSynsets method to read the hypernyms.txt file and
     * displaying in the console (or terminal).
     * 
     * @param filename string that contains filename with extension
     */
    private void parseHypernyms(String filename) throws FileNotFoundException, IOException {
        File  read_hypernyms = new File(filename); // loads the file 
        BufferedReader br = new BufferedReader(new FileReader(read_hypernyms)); // reads the file
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