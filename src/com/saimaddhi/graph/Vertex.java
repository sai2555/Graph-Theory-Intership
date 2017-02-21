package com.saimaddhi.graph;
import java.util.ArrayList;

import edu.princeton.cs.algs4.StdDraw;
/**
 * This is the Vertex class that represents a vertex in an undirected graph
 * @author saimaddhi
 *
 */
public class Vertex {
	/**
	 * The "name" of this vertex
	 */
	private String label;
	/**
	 * True if the vertex has been given in the current traversal
	 */
	private boolean visited;
	/**
	 * The x coordinate of this vertex
	 */
	private double xCoor;
	/**
	 * The y coordinate of this vertex
	 */
	private double yCoor;
	/**
	 * True if the mouse is over the button
	 */
	private boolean lightGray;
	/**
	 * True if the button is in the clicked state
	 */
	private boolean clicked;
	/**
	 * True if the button is allowed to be clicked
	 */
	private boolean isClickable;
	/**
	 * The array list of the vertex's neighbors
	 */
	private ArrayList<Vertex> neighbors;
	/**
	 * The Vertex constructor that takes in a name
	 * @param label
	 */
	public Vertex(String label) {
		this.label = label;
		this.lightGray = false;
		this.clicked = false;
		this.isClickable = true;
		this.visited = false;
		neighbors = new ArrayList<Vertex>(0);
	}
	/**
	 * Setter for the label field
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Getter for the label field
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}
	/**
	 * Getter for the visited field
	 * @return has been visited or not
	 */
	public boolean isVisited() {
		return visited;
	}
	/**
	 * setter for the visited field
	 * @param visited the new visited
	 */
	public void isVisited(boolean visited) {
		this.visited = visited;
	}
	/**
	 * Adds this vertex to the current neighbors
	 * @param vertex
	 */
	public void addNeighbor(Vertex vertex) {
		neighbors.add(vertex);
	}
	/**
	 * This method removes the specified neighbor from the neighbors list
	 * @param vertex the vertex to remove
	 * @return true if the resulting size is 0
	 */
	public boolean removeNeighbor(Vertex vertex) {
		for(int i = 0; i < neighbors.size(); i++) {
			if(neighbors.get(i).equals(vertex)) {
				neighbors.remove(i);
				return neighbors.size() == 0;
			}
		}
		return false;
	}
	/**
	 * getter for the neighbors list of this vertex
	 * @return the neighbors list
	 */
	public ArrayList<Vertex> getNeighbors() {
		return neighbors;
	}
	/**
	 * The toString for this vertex that formats the resulting string as such: label [neighboring vertices]
	 */
	public String toString() {
		String toString = label + " [";
		if(neighbors.size() == 0) {
			toString += "]";
		} else {
			for(Vertex vertex: neighbors) {
				toString += vertex.label + ", ";
			}
			toString = toString.substring(0,toString.length()-2) + "]";
		}
		return toString;
	}
	/**
	 * The equals method for the vertex class that results in true if both vertices have the same name
	 */
	public boolean equals(Object v) {
		return (label.equals(((Vertex) v).label));
	}
	/**
	 * Sets the coordinates for this specific vertex
	 * @param xCoor x coordinate
	 * @param yCoor y coordinate
	 */
	public void setCoors(double xCoor, double yCoor) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}
	/**
	 * The draw method for the Vertex that draws the circle with a label inscribed in the middle
	 */
	public void draw() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(xCoor, yCoor, 50);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.circle(xCoor, yCoor, 50);
		StdDraw.text(xCoor, yCoor, label);
	}
	/**
	 * The draw method for the Vertex that draws the circle with the label inscribed in the middle which formats the vertex with
	 * the colors of the minimum spanning tree
	 */
	public void drawMST() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledCircle(xCoor, yCoor, 50);
		StdDraw.setPenColor(21, 105, 76);
		StdDraw.circle(xCoor, yCoor, 50);
		StdDraw.text(xCoor, yCoor, label);
	}
	/**
	 * Tracks the mouse in relation with the vertex
	 * @return true if the vertex is in the clicked state
	 */
	public boolean checkMouse() {
		if(clicked) {
			if(Math.abs(StdDraw.mouseX() - xCoor) < 50 && Math.abs(StdDraw.mouseY() - yCoor) < 50) {
				if(!lightGray) {
					StdDraw.setPenColor(StdDraw.DARK_GRAY);
					StdDraw.filledCircle(xCoor, yCoor, 50);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.circle(xCoor, yCoor, 50);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.text(xCoor, yCoor, label);
					lightGray = true;
				}
			} else {
				if(lightGray) {
					isClickable = true;
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle(xCoor, yCoor, 50);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.text(xCoor, yCoor, label);
					lightGray = false;
				}
			}
			if(isClickable && StdDraw.mousePressed() && lightGray) {
				draw();
				clicked = false;
				lightGray = true;
				isClickable = false;
				return false;
			} else {
				return true;
			}
		} else {
			if(Math.abs(StdDraw.mouseX() - xCoor) < 50 && Math.abs(StdDraw.mouseY() - yCoor) < 50) {
				if(!lightGray) {
					StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
					StdDraw.filledCircle(xCoor, yCoor, 50);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.circle(xCoor, yCoor, 50);
					StdDraw.text(xCoor, yCoor, label);
					lightGray = true;
				}
			} else {
				if(lightGray) {
					isClickable = true;
					lightGray = false;
					StdDraw.setPenColor(StdDraw.WHITE);
					draw();
				}
			}
			if(isClickable && StdDraw.mousePressed() && lightGray) {
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.filledCircle(xCoor, yCoor, 50);
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.text(xCoor, yCoor, label);
				clicked = true;
				isClickable = false;
				return true;
			} else {
				return false;
			}
		}
	}
	/**
	 * Getter for the clicked state
	 * @return the clicked state
	 */
	public boolean beenClicked() {
		return clicked;
	}
	/**
	 * Resets the vertex to the initial state
	 */
	public void reset() {
		this.lightGray = false;
		this.clicked = false;
		this.isClickable = true;
	}
	/**
	 * The getter for the x coordinate of the vertex
	 * @return
	 */
	public double xCoor() {
		return this.xCoor;
	}
	/**
	 * The getter for the y coordinate of the vertex
	 * @return
	 */
	public double yCoor() {
		return this.yCoor;
	}
}