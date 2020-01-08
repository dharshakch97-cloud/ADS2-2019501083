import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * We are creating the WordNet which is a bot that developed by IBM to answer some questions.
 * We are having hypernyms and synsets
 */

public class WordNet {

    static ArrayList<String> hnid = new ArrayList<String>();    //Id of hypernyms
    static ArrayList<String[]> links = new ArrayList<String[]>();// links of the hypernyms

    /**
     * Default Constructor
     */
    public WordNet() {

    }

    /**
     * It helps in reading the file and splitting the words in the file and give the links as array of strings.
     * It stores the data in hnid ad links of hypernyms
     * this can be used to create a hash table
     * complexity of this method is O(N)
     * @return no return value and type
     * @throws NoSuchElementException
     * @throws FileNotFoundException
     */
    public void parseHypernyms() throws NoSuchElementException,FileNotFoundException  {
        String[] intArray = new String[3];
            String fileName = "hypernyms.txt";
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            Scanner br = new Scanner(fr);
            String line;
            while (br.hasNextLine()) {
                line= br.nextLine();
                String[] str=line.split(",",2);
                if (str.length >1){
                hnid.add(str[0]);
                links.add(str[1].split(","));}
                else {
                    hnid.add(str[0]);
                    links.add(null);
                }
            }
        }

    public static void main(String[] args) throws Exception {
        WordNet wn = new WordNet();
        wn.parseHypernyms();            //hypernyms method calls
        Linearprobing<String,String[]> lp = new Linearprobing<String,String[]>(); //hypernyms object
        Digraph dg = new Digraph(hnid.size());

        for (int i=0;i<hnid.size();i++){
            if (links.get(i)!= null){
            for (int j = 0; j < links.get(i).length; j++) {
                int x= Integer.parseInt(hnid.get(i));
                int y = Integer.parseInt(links.get(i)[j]);
                dg.addEdge(x, y);
            }}
        }

        // for (int i = 0; i < hnid.size(); i++) {   //inserting the sypernyms
        //     lp.put(hnid.get(i),links.get(i));
        // }

        System.out.println(dg.toString());
   }
}