package com.saimaddhi.graph;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
/**
 * The graph class
 * @author saimaddhi
 *
 */
public class Graph {
	/**
	 * The hashmap of the vertices
	 */
	private HashMap<String, Vertex> vertices;
	/**
	 * The list of edges of the graph
	 */
	private ArrayList<Edge> edges;
	/**
	 * The default constructor for the graph class
	 */
	public Graph() {
		vertices = new HashMap<String, Vertex>(0);
		edges = new ArrayList<Edge>(0);
	}
	/**
	 * The addEdge Method that adds an edge to the graph
	 * @param startLabel the start vertex name
	 * @param endLabel the end vertex name
	 * @param weight the weight of the edge
	 */
	public void addEdge(String startLabel, String endLabel, int weight) {
		Edge find = new Edge(new Vertex(startLabel), new Vertex(endLabel), weight);
		if(edges.contains(find) ||
				edges.contains(new Edge(new Vertex(endLabel), new Vertex(startLabel), weight))) return;
		Vertex start = vertices.get(startLabel);
		Vertex end = vertices.get(endLabel);
		if(start == null) start = addVertex(startLabel);
		if(end == null) end = addVertex(endLabel);
		start.addNeighbor(end);
		end.addNeighbor(start);
		edges.add(new Edge(start, end, weight));
	}
	/**
	 * The addEdge Method that adds an edge to the graph, however this one allows adding duplicates(used by the UI Drawer)
	 * @param startLabel the start vertex name
	 * @param endLabel the end vertex name
	 * @param weight the weight of the edge
	 */
	public void addEdgeAllowDuplicates(String startLabel, String endLabel, int weight) {
		Vertex start = vertices.get(startLabel);
		Vertex end = vertices.get(endLabel);
		if(start == null) start = addVertex(startLabel);
		if(end == null) end = addVertex(endLabel);
		start.addNeighbor(end);
		end.addNeighbor(start);
		edges.add(new Edge(start, end, weight));
	}
	/**
	 * The add Vertex method that adds and returns the added vertex
	 * @param label the vertex to be added
	 * @return the added vertex
	 */
	public Vertex addVertex(String label) {
		vertices.put(label, new Vertex(label));
		return vertices.get(label);
	}
	/**
	 * The addEdges from file method that adds edges from the specified file
	 * @param fileName the file name
	 */
	public void addEdgesFromFile(String fileName) {
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			String line = "";
			int weight;
			String startLabel;
			String endLabel;
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				startLabel = line.substring(0,line.indexOf(" "));
				endLabel = line.substring(line.indexOf(" ")+1, line.lastIndexOf(" "));
				weight = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));
				addEdge(startLabel, endLabel, weight);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method returns the depth first traversal of the graph starting from the specified vertex
	 * @param label the starting label
	 * @return the list of the vertices part of the traversal
	 */
	public ArrayList<Vertex> depthFirstTraversal(String label) {
		Vertex vertex = vertices.get(label);
		ArrayList<Vertex> vertexs = new ArrayList<Vertex>(0);
		if(vertex == null) {
			return vertexs;
		} else {
			Stack<Vertex> stack = new Stack<Vertex>();
			stack.add(vertex);
			vertex.isVisited(true);
			while(!stack.isEmpty()) {
				Vertex old = stack.pop();
				vertexs.add(old);
				for(Vertex v: old.getNeighbors()) {
					if(!v.isVisited()) {
						stack.add(v);
						v.isVisited(true);
					}
				}
			}
		}
		resetVertices();
		return vertexs;
	}
	/**
	 * returns the breadth first traversal of the graph starting from the specified vertex
	 * @param label the specified vertex
	 * @return the list of vertices part of the breadth first traversal
	 */
	public ArrayList<Vertex> breadthFirstTraversal(String label) {
		Vertex vertex = vertices.get(label);
		ArrayList<Vertex> vertexs = new ArrayList<Vertex>(0);
		if(vertex == null) {
			return vertexs;
		} else {
			Queue<Vertex> queue = new Queue<Vertex>();
			queue.push(vertex);
			vertex.isVisited(true);
			while(!queue.isEmpty()) {
				for(Vertex v: queue.peek().getNeighbors()) {
					if(!v.isVisited()) {
						queue.push(v);
						v.isVisited(true);
					}
				}
				vertexs.add(queue.pop());
			}
		}
		resetVertices();
		return vertexs;
	}
	/**
	 * Resets the vertices and makes all of their visited values false
	 */
	private void resetVertices() {
		for(Vertex v: vertices.values()) {
			v.isVisited(false);
		}
	}
	/**
	 * Returns the list of the edges part of the minimum spanning tree
	 * @return the list of edges part of the mst
	 */
	public ArrayList<Edge> minimumSpanningTree() {
		ArrayList<Edge> vertexs = new ArrayList<Edge>();
		Edge[] branches = new Edge[this.edges.size()];
		Graph graph = new Graph();
		Edge edge;
		boolean isCyclic;
		int index = 0;
		this.resetVertices();
		
		//Sort Edges
		for(int i = 0; i < this.edges.size(); i++) {
			branches[i] = this.edges.get(i);
		}
		Arrays.sort(branches);
		
		//Keep adding lowest noncyclic edges to "vertexs"
		while(vertexs.size() < vertices.values().size() - 1) {
			edge = branches[index];
			graph.addEdge(edge.getStart().getLabel(), edge.getEnd().getLabel(), edge.getWeight());
			edge = graph.edges.get(graph.edges.size() - 1);
			
			Vertex start = edge.getStart();
			start.isVisited(true);
			graph.resetVertices();
			isCyclic = graph.isCyclical(null, start, false);
			
			if(isCyclic) {
				graph.removeEdge(edge);
			} else {
				vertexs.add(edge);
			}
			index++;
		}
		return vertexs;
	}
	/**
	 * Returns true if the graph has cycles
	 * @param parent the parent of the vertex that is currently being recursed on
	 * @param current the current vertex being recursed on
	 * @param isCyclic true if the graph contains cycles
	 * @return true if the graph contains cycles
	 */
	private boolean isCyclical(Vertex parent, Vertex current, boolean isCyclic) {
		current.isVisited(true);
		if(isCyclic) return true;
		for(Vertex vertex : current.getNeighbors()) {
			if(vertex.isVisited()) {
				if(parent == null) parent = new Vertex(vertex.getLabel() + "notThis"); 
				if(!vertex.equals(parent)) {
					isCyclic = true;
					return true;
				} else {
					continue;
				}
			} else {
				if(isCyclical(current, vertex, isCyclic)) {
					isCyclic = true;
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Displays the MST statistics to the console
	 */
	public void displayStatsMST() {
		ArrayList<Edge> mst = minimumSpanningTree();
		System.out.println("Minimum Spanning Tree Stats:\nEdges - ");
		int total = 0;
		for(Edge e : mst) {
			System.out.println(e);
			total += e.getWeight();
		}
		System.out.println("\nTotal Weight: " + total);
	}
	/**
	 * This method removes the specified edge from the graph
	 * @param e the edge to be removed
	 */
	public void removeEdge(Edge e) {
		for(int i = 0; i < edges.size(); i++) {
			if(edges.get(i).equals(e)) {
				Vertex end = e.getEnd();
				Vertex start = e.getStart();	
				if(end.removeNeighbor(start)) {
					vertices.remove(end.getLabel());
				}
				if(start.removeNeighbor(end)) {
					vertices.remove(start.getLabel());
				}
				edges.remove(i);
				break;
			}
		}
	}
	/**
	 * Getter for the vertices hashmap
	 * @return the vertices hashmap
	 */
	public HashMap<String, Vertex> getVertices() {
		return vertices;
	}
	/**
	 * The getter for the list of edges
	 * @return the edge list
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	/**
	 * The toString of the graph that prints out the vertices of the graph
	 */
	public String toString() {
		String result = (vertices.size() == 0) ? "a" : "";
		for(Vertex vertex: vertices.values()) {
			result += vertex;
			result += "\n";
		}
		return result.substring(0, result.length());
	}
}