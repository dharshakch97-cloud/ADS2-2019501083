/** 
 * @author Dharshak
 * 
 * This SAP class used to find the shortest path between the given 
 * two vertices and find the shortest ancestor path between the them
*/
import java.util.*;
import java.lang.*;
import java.io.*;

public class SAP {

    Digraph digraph; // Digraph object creation 'digraph' 
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        // if (G == null) {
        //     throw IllegalArgumentException("Graph is null");
        // }
        digraph = new Digraph(G);
    }

    /** 
     * @param v integer
     *
     * This method checks the vertex is valid vertex
     * in the graph or not
     * If vertex in the graph is not valid, throws 'InexOutOfBoundsException'
     * and displays 'vertex is not between 0 and total vertices' 
    */
    private void validVertex(int v) {
        int V = digraph.V();
        if (v < 0 && v >= V) {
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
        }
    }
 
    /** 
     * @param vertices Iterable<Integer>
     * 
     * This method checks the Iterable of vertices 
     * are valid or not
     * If vertex in the graph is not valid, throws 'InexOutOfBoundsException'
     * and displays 'vertex is not between 0 and total vertices' 
    */
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

    /** 
     * @param v integer
     * @param w integer
     *
     * This method 'shortest' used to find 
     * return the shortest length between 
     * two vertices and its corresponding 
     * common ancestor
    */
    private int[] shortest(int v, int w) {
        validVertex(v); // validating the vertex v
        validVertex(w); // validating the vertex v
        int[] res = new int[2];
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v); // bfs for v vertex
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w); // bfs for v vertex
        
        int shortest_Len = Integer.MAX_VALUE; // stores Integer MAX_VALUE as default
        int shortest_Ancestor = -1; // stores -1 as default
        // checking for the paths for both of the vertices and finding the length of both the paths
        // Then, if it is shortest length update the shortest_Len and the vertex 'i' as the common ancestor 
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                int len = bfsv.distTo(i) + bfsw.distTo(i);
                if (len < shortest_Len) {
                    shortest_Len = len;
                    shortest_Ancestor = i;
                }
            }
        }
        // if the shortest_Ancestor is -1, it has no path
        // So, update shortest_Len and shortest_Ancestor as -1
        // Or else, update its actual shortest_Len and shortest_Ancestor
        if (shortest_Ancestor == -1) {
            res[0] = -1;
            res[1] = -1;
        }
        else {
            res[0] = shortest_Len;
            res[1] = shortest_Ancestor;
        }
        return res; // returns the shortest_Len and shortest_Ancestor as a array
    }

    /** 
     * @param v Iterable integer
     * @param w Iterable integer
     *
     * This method 'shortest' used to find 
     * return the shortest length between 
     * two vertices and its corresponding 
     * common ancestor
    */
    private int[] shortest(Iterable<Integer> v, Iterable<Integer> w) {
        validVertices(v); // validating the vertex v
        validVertices(w); // validating the vertex w 
        int[] res = new int[2];
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v); // bfs for v vertex
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w); // bfs for w vertex
        
        int shortest_Len = Integer.MAX_VALUE; // stores Integer MAX_VALUE as default
        int shortest_Ancestor = -1; // stores -1 as deault
        // checking for the paths for both of the vertices and finding the length of both the paths
        // Then, if it is shortest length update the shortest_Len and the vertex 'i' as the common ancestor 
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) { 
                int len = bfsv.distTo(i) + bfsw.distTo(i);
                if (len < shortest_Len) {
                    shortest_Len = len;
                    shortest_Ancestor = i;
                }
            }
        }
        // if the shortest_Ancestor is -1, it has no path
        // So, update shortest_Len and shortest_Ancestor as -1
        // Or else, update its actual shortest_Len and shortest_Ancestor
        if (shortest_Ancestor == -1) {
            res[0] = -1;
            res[1] = -1;
        }
        else {
            res[0] = shortest_Len;
            res[1] = shortest_Ancestor;
        }
        return res; // returns the shortest_Len and shortest_Ancestor as a array
    }

    /** 
     * @param v integer 
     * @param w integer
     *
     * To find the length of shortest ancestral path between v and w
     * if no such path, returns -1 
    */
    public int length(int v, int w) {
        int[] result = shortest(v, w);
        return result[0];
    }
 
    /** 
     * @param v integer 
     * @param w integer
     *
     * To find the common ancestor of v and w that present in a shortest ancestral path
     * if no such path, returns -1
    */
    public int ancestor(int v, int w) {
        int[] result = shortest(v, w);
        return result[1];
    }
 
    /** 
     * @param v An Iterable integer 
     * @param w An Iterable integer
     *
     * To find the length of shortest ancestral path between v and w
     * if no such path, returns -1 
    */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = shortest(v, w);
        return result[0];
    }
 
    /** 
     * @param v An Iterable integer 
     * @param w An Iterable integer
     *
     * To find the common ancestor of v and w that present in a shortest ancestral path
     * if no such path, returns -1
    */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = shortest(v, w);
        return result[1];
    }
 
    // do unit testing of this class
    // public static void main(String[] args)
 }