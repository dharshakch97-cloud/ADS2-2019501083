import java.util.*;
import java.lang.*;
import java.io.*;

public class SAP {

    Digraph digraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        // if (G == null) {
        //     throw IllegalArgumentException("Graph is null");
        // }
        digraph = new Digraph(G);
    }
 
    private void validVertex(int v) {
        int V = digraph.V();
        if (v < 0 && v >= V) {
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
        }
    }
 
    private void validVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new NullPointerException("argument is null");
        }
        int V = digraph.V();
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }

    // return shortest length and corresponding ancestor
    private int[] shortest(int v, int w) {
        validVertex(v);
        validVertex(w);
        int[] res = new int[2];
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w);
        
        int shortest_Len = Integer.MAX_VALUE;
        int shortest_Ancestor = -1;
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                int len = bfsv.distTo(i) + bfsw.distTo(i);
                if (len < shortest_Len) {
                    shortest_Len = len;
                    shortest_Ancestor = i;
                }
            }
        }
        
        if (shortest_Ancestor == -1) {
            res[0] = -1;
            res[1] = -1;
        }
        else {
            res[0] = shortest_Len;
            res[1] = shortest_Ancestor;
        }
        return res;
    }

    // return shortest length and corresponding ancestor
    private int[] shortest(Iterable<Integer> v, Iterable<Integer> w) {
        validVertices(v);
        validVertices(w);
        int[] res = new int[2];
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w);
        
        int shortest_Len = Integer.MAX_VALUE;
        int shortest_Ancestor = -1;
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                int len = bfsv.distTo(i) + bfsw.distTo(i);
                if (len < shortest_Len) {
                    shortest_Len = len;
                    shortest_Ancestor = i;
                }
            }
        }
        
        if (shortest_Ancestor == -1) {
            res[0] = -1;
            res[1] = -1;
        }
        else {
            res[0] = shortest_Len;
            res[1] = shortest_Ancestor;
        }
        return res;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int[] result = shortest(v, w);
        return result[0];
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        int[] result = shortest(v, w);
        return result[1];
    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = shortest(v, w);
        return result[0];
    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = shortest(v, w);
        return result[1];
    }
 
    // do unit testing of this class
    // public static void main(String[] args)
 }