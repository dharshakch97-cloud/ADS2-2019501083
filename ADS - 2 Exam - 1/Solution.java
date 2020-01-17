import java.util.*;

class Solution {

	private LinearProbingHashST<String, Integer> htable;
	private LinearProbingHashST<Integer, Integer> htable1;

	private static Digraph d;

	public Solution(String et, String et_logs) {
        if (et == null || et_logs == null) {
            throw new IllegalArgumentException("File name is null");
        }
        readEmailsTxt(et);
        readEmailLogs(et_logs);
    }

    private void readEmailsTxt(String et) {
    	htable = new LinearProbingHashST<String, Integer>();

    	In in_et = new In(et);
    	while (in_et.hasNextLine()) {
			String[] str = in_et.readLine().split(";");
			int d1 = Integer.parseInt(str[0]);
			
			htable.put(str[1], d1);
		}
    }

	private void readEmailLogs(String et_logs) {

		ArrayList<Integer> sdr = new ArrayList<Integer>();
        ArrayList<Integer> rcvr = new ArrayList<Integer>();

    	In in_etLogs = new In(et_logs);
    	while (in_etLogs.hasNextLine()) {
    		String[]  str = in_etLogs.readLine().split(", ");

    		String[] d1 = str[0].split(" ");
    		sdr.add(Integer.parseInt(d1[1]));

    		String[] d2 = str[1].split(" ");
    		rcvr.add(Integer.parseInt(d2[1]));
    	}
    	
    	d = new Digraph(sdr.size());
    	int e = 0;
    	for (int i = 0; i < sdr.size(); i++) {
    		d.addEdge(sdr.get(i), rcvr.get(i));
    		e++;
    	}
    }

    public void indegree_count(int n) {
    	int max = 0;
    	ArrayList<Integer> index = new ArrayList<Integer>();
    	ArrayList<Integer> indegList = new ArrayList<Integer>();
    	htable1= new LinearProbingHashST<Integer, Integer>();
    	int[] top_n = new int[n];
    	int c = 0;
    	for (int i = 0; i < d.V(); i++) {
    		int indegCount = d.indegree(i);
    		if (indegCount > max) {
    			htable1.put(indegCount, i);
    			indegList.add(indegCount);
    			index.add(i);
    		}
    	}
    	
    	for (int i = 0; i < n; i++) {
    		top_n[c++] = Collections.max(indegList);
    		indegList.remove(Collections.max(indegList));
    	}

    	for (int i = 0; i < index.size(); i++) {
    		System.out.println(htable1.get(i) + ", " + top_n[i]);
    	}
    }

	public static void main(String[] args) {

		String email_txt = "emails.txt";
		String email_log = "email-logs.txt";
		Solution s = new Solution(email_txt, email_log);

		int n = Integer.parseInt(args[0]);
		s.indegree_count(n);
	}
}