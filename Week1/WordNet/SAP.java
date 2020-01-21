// importing required class files from edu.princeton.cs.algs4

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import java.lang.IllegalArgumentException;

import java.util.Arrays;

/** 
 * @author Dharshak
 * 
 * This SAP class used to find the shortest path between the given 
 * two vertices and find the shortest ancestor path between the them
*/

public class SAP {

    // Digraph object creation 'digraph'
    private final Digraph digraph; 
    /** 
     * @param G Digraph G
     *
     * This method takes the input 'G' (digraph)
     * and creates object for the graph 'G'
    */

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
        int noOfVertices = digraph.V();
        if (v < 0 && v >= noOfVertices) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (noOfVertices-1));
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
            throw new IllegalArgumentException("argument is null");
        }
        int noOfVertices = digraph.V();
        for (int v : vertices) {
            if (v < 0 || v >= noOfVertices) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (noOfVertices-1));
            }
        }
    } 

    /** 
     * @param v input 'v' integer
     * @param w input 'w' integer
     *
     * This method 'shortest' used to find and 
     * return the shortest length between 
     * two vertices and its corresponding common ancestor
    */

    private int[] shortest(int v, int w) {
        validVertex(v); // check if the input vertex 'v' is valid vertex or not
        validVertex(w); // check if the input vertex 'w' is valid vertex or not
        int[] res = new int[2];
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v); // bfs for 'v' vertex
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w); // bfs for 'w' vertex
        
        int shortestLen = Integer.MAX_VALUE; // stores Integer MAX_VALUE as default
        int shortestAncestor = -1; // stores -1 as default
        // checking for the paths for both of the vertices and finding the length of both the paths
        // Then, if it is shortest length update the shortest_Len and the vertex 'i' as the common ancestor 
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                int len = bfsv.distTo(i) + bfsw.distTo(i);
                if (len < shortestLen) {
                    shortestLen = len;
                    shortestAncestor = i;
                }
            }
        }
        // if the shortest_Ancestor is -1, it has no path
        // So, update shortest_Len and shortest_Ancestor as -1
        // Or else, update its actual shortest_Len and shortest_Ancestor
        if (shortestAncestor == -1) {
            res[0] = -1;
            res[1] = -1;
        }
        else {
            res[0] = shortestLen;
            res[1] = shortestAncestor;
        }
        return res; // returns the shortest_Len and shortest_Ancestor as a array
    }

    /** 
     * @param v input 'v' Iterable integer
     * @param w input 'w' Iterable integer
     *
     * This method 'shortest' used to find and
     * return the shortest length between 
     * two vertices and its corresponding common ancestor
    */

    private int[] shortest(Iterable<Integer> v, Iterable<Integer> w) {
        validVertices(v); // check if the input vertex 'v' is valid vertex or not
        validVertices(w); // check if the input vertex 'w' is valid vertex or not
        int[] res = new int[2];
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v); // bfs for 'v' vertex
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w); // bfs for 'w' vertex
        
        int shortestLen = Integer.MAX_VALUE; // stores Integer MAX_VALUE as default
        int shortestAncestor = -1; // stores -1 as deault
        // checking for the paths for both of the vertices and finding the length of both the paths
        // Then, if it is shortest length update the shortest_Len and the vertex 'i' as the common ancestor 
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) { 
                int len = bfsv.distTo(i) + bfsw.distTo(i);
                if (len < shortestLen) {
                    shortestLen = len;
                    shortestAncestor = i;
                }
            }
        }
        // if the shortest_Ancestor is -1, it has no path
        // So, update shortest_Len and shortest_Ancestor as -1
        // Or else, update its actual shortest_Len and shortest_Ancestor
        if (shortestAncestor == -1) {
            res[0] = -1;
            res[1] = -1;
        }
        else {
            res[0] = shortestLen;
            res[1] = shortestAncestor;
        }
        return res; // returns the shortest_Len and shortest_Ancestor as a array
    }

    /** 
     * @param v input 'v' integer 
     * @param w input 'w' integer
     *
     * To find the length of shortest ancestral path between v and w
     * if no such path, returns -1 
    */
    
    public int length(int v, int w) {
        int[] result = shortest(v, w);
        return result[0];
    }
 
    /** 
     * @param v input 'v' integer 
     * @param w input 'w' integer
     *
     * To find the common ancestor of v and w that present in a shortest ancestral path
     * if no such path, returns -1
    */

    public int ancestor(int v, int w) {
        int[] result = shortest(v, w);
        return result[1];
    }
 
    /** 
     * @param v input 'v' Iterable integer 
     * @param w input 'w' Iterable integer
     *
     * To find the length of shortest ancestral path between v and w
     * if no such path, returns -1 
    */

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (Arrays.asList(v).contains(null) || Arrays.asList(w).contains(null)) {
            throw new IllegalArgumentException("argument is null");
        } 
        int[] result = shortest(v, w);
        return result[0];
    }
 
    /** 
     * @param v input 'v' Iterable integer 
     * @param w input 'w' Iterable integer
     *
     * To find the common ancestor of v and w that present in a shortest ancestral path
     * if no such path, returns -1
    */

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (Arrays.asList(v).contains(null) || Arrays.asList(w).contains(null)) {
            throw new IllegalArgumentException("argument is null");
        }
        int[] result = shortest(v, w);
        return result[1];
    }
 
    // do unit testing of this class
    // public static void main(String[] args)
 }