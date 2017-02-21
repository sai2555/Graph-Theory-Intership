package com.saimaddhi.graph;
import edu.princeton.cs.algs4.StdDraw;

/**
 * This class represents an Edge in an undirected graph
 * @author saimaddhi
 *
 */
public class Edge implements Comparable {
	/**
	 * The Vertex the edge starts at
	 */
	private Vertex start;
	/**
	 * The Vertex the edge ends at
	 */
	private Vertex end;
	/**
	 * The weight of this edge
	 */
	private int weight;
	/**
	 * Constructor that initializes the edge with a start vertex, end vertex, and a specified weight
	 * @param start the start vertex
	 * @param end the end vertex
	 * @param weight the specified weight
	 */
	public Edge(Vertex start, Vertex end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	/**
	 * This is the compare to method that allows the edges to be sorted easily
	 */
	public int compareTo(Object other) {
		return this.weight - ((Edge)other).weight;
	}
	/**
	 * This method returns the start vertex of the edge
	 * @return the start vertex
	 */
	public Vertex getStart() {
		return this.start;
	}
	/**
	 * This method returns the end vertex of this edge
	 * @return the end Vertex
	 */
	public Vertex getEnd() {
		return this.end;
	}
	/**
	 * This method returns the weight of the edge
	 * @return the weight of the edge
	 */
	public int getWeight() {
		return this.weight;
	}
	/**
	 * This method allows to change the start vertex of this edge
	 * @param start the new start edge
	 */
	public void setStart(Vertex start) {
		this.start = start;
	}
	/**
	 * This method allows to change the end vertex of this edge
	 * @param end the new end vertex
	 */
	public void setEnd(Vertex end) {
		this.end = end;
	}
	/**
	 * This method allows to change the weight of this edge
	 * @param weight the new weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/**
	 * The toString of this method returns a string in the format: [start end : weight]
	 */
	public String toString() {
		return "[" + start.getLabel() + " " + end.getLabel() + " : " + weight + "]";
	}
	/**
	 * The equals method for this class that returns true if both the start and end vertices are the same for both edges
	 */
	public boolean equals(Object e) {
		return (start.equals(((Edge) e).start)) && (end.equals(((Edge) e).end));
	}
	/**
	 * The draw method for this edge class that draws a line from start to end
	 */
	public void draw() {
		StdDraw.line(this.getStart().xCoor(), this.getStart().yCoor(),
				this.getEnd().xCoor(), this.getEnd().yCoor());
	}
}