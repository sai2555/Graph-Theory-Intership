package com.saimaddhi.graph;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.princeton.cs.algs4.StdDraw;
/**
 * This class creates a UI representation for the graph
 * @author saimaddhi
 *
 */
public class Grapher {
	/**
	 * The graph being represented
	 */
	private Graph graph;
	/**
	 * The vertices of the graph
	 */
	private ArrayList<Vertex> vertexs;
	/**
	 * The edges of the graph
	 */
	private ArrayList<Edge> edges;
	/**
	 * The add edge button
	 */
	private AddEdgeButton edgeButton;
	/**
	 * The add vertex button
	 */
	private AddVertexButton vertexButton;
	/**
	 * The show MST button
	 */
	private MSTButton mstButton;
	/**
	 * True if the graph is currently showing the MST
	 */
	private boolean mstMode;
	/**
	 * The constructor of this class that takes in a graph to be represented
	 * @param graph the graph being represented
	 */
	public Grapher(Graph graph) {
		this.graph = graph;
		vertexs = new ArrayList<Vertex>(graph.getVertices().values());
		edges = graph.getEdges();
		edgeButton = new AddEdgeButton(925, 75, 50);
		vertexButton = new AddVertexButton(1035, 185, 50);
		mstButton = new MSTButton(1035, 75, 50);
		mstMode = false;
		
		setup();
		setUpVertices();
		run();
	}
	/**
	 * Runs the UI Program
	 */
	public void run() {
		boolean edgeClicked = false;
		boolean vertexClicked = false;
		boolean mstClicked = false;
		boolean reset = true;
		boolean shown = false;
		int count = 0;
		
		while(true) {
			if(edgeClicked) {
				edgeClicked = edgeButton.checkMouse();
				if(!edgeClicked) reset = true;
				count = 0;
				for(Vertex v : vertexs) {
					if(v.checkMouse()) count++;
				}
				if(count == 2) {
					addEdgeIfNeeded();
					reset = true;
					edgeClicked = false;
				}
			} else if(vertexClicked) {
				String input = "";
				
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.clear();
				StdDraw.text(550, 800, "Type in the name of the new Vertex, and then press enter!");
				StdDraw.show();
				while(!StdDraw.isKeyPressed(KeyEvent.VK_ENTER) || input.length() == 0) {
					if(StdDraw.hasNextKeyTyped()) {
						input += StdDraw.nextKeyTyped();
						StdDraw.clear();
						StdDraw.text(550, 800, "Type in the name of the new Vertex, and then press enter!");
						StdDraw.text(550, 600, "Vertex Name: " + input);
						StdDraw.show();
					}
				}
				
				StdDraw.clear();
				StdDraw.setFont(new Font("Arial", Font.BOLD, 60));
				StdDraw.text(550, 550, "Done!!!");
				StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
				StdDraw.show();
				
				reset = true;
				vertexClicked = false;
				graph.addVertex(input);
			} else if(mstClicked) {
				mstClicked = mstButton.checkMouse();
				mstMode = true;
				ArrayList<Edge> tree = new ArrayList<Edge>();
				if(!shown) {
					shown = true;
					tree = graph.minimumSpanningTree();
					StdDraw.clear();
					mstButton.checkMouse();
					draw();
					StdDraw.setPenColor(87, 248, 95);
					for(Edge e : tree) {
						graph.addEdgeAllowDuplicates(e.getStart().getLabel(), e.getEnd().getLabel(), e.getWeight());
						graph.getEdges().get(graph.getEdges().size()-1).draw();
						graph.removeEdge(graph.getEdges().get(graph.getEdges().size()-1));
					}
					//Draw Vertices
					StdDraw.setPenColor(87, 248, 95);
					for(Vertex v : vertexs) {
						v.drawMST();
						v.reset();
					}
				}
				if(!mstClicked) {
					reset = true;
				}
			} else {
				if(reset) {
					StdDraw.clear();
					StdDraw.setPenColor(StdDraw.BLACK);
					edgeButton.reset();
					vertexButton.reset();
					mstButton.reset();
					mstMode = false;
					reset = false;
					shown = false;
					vertexs = new ArrayList<Vertex>(graph.getVertices().values());
					setUpVertices();
					draw();
				} else {
					edgeClicked = edgeButton.checkMouse();
					vertexClicked = vertexButton.checkMouse();
					mstClicked = mstButton.checkMouse();
				}
			}
			StdDraw.show();
		}
	}
	/**
	 * Sets up standard draw(window, etc.)
	 */
	private void setup() {
		StdDraw.setXscale(0.0, 1100.0);
		StdDraw.setYscale(0.0, 1100.0);
		StdDraw.setPenRadius(0.005);
	}
	/**
	 * This method sets up the vertices such that all are in their corresponding places of the circle
	 */
	private void setUpVertices() {
		int index = 1;
		double xCoor;
		double yCoor;
		
		for(Vertex v : vertexs) {
			xCoor = 500.0 + getX(index, vertexs.size());
			yCoor = 500.0 + getY(index, vertexs.size());
			v.setCoors(xCoor, yCoor);
			index++;
		}
	}
	/**
	 * The draw method of the graph that depicts all of the vertices
	 */
	private void draw() {
		if(mstMode) {
			//Draw Edges
			StdDraw.setPenColor(StdDraw.BLACK);
			drawEdges();
			//Title
			StdDraw.setFont(new Font("Arial", Font.BOLD, 40));
			StdDraw.setPenColor(21, 105, 76);
			StdDraw.text(550, 1000, "Minimum Spanning Tree");
			StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
		} else {
			StdDraw.setPenColor(StdDraw.BLACK);
			drawEdges();
			drawVertices();
			StdDraw.setFont(new Font("Arial", Font.BOLD, 60));
			StdDraw.text(550, 1000, vertexs.size() + "-Point Graph");
			StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
		}
	}
	/**
	 * The draw Vertices method that draws each vertex in the graph
	 */
	private void drawVertices() {
		for(Vertex v : vertexs) {
			v.draw();
			v.reset();
		}
	}
	/**
	 * The draw edge method that draws each edge of the graph
	 */
	private void drawEdges() {
		for(Edge e : edges) {
			e.draw();
		}
	}
	/**
	 * Returns the x coordinate of the vertex based on its location in the polygon formation
	 * @param index the vertex number of this vertex
	 * @param numOfVertices the total number of vertices
	 * @return the x coordinate of the vertex's position
	 */
	private double getX(double index, double numOfVertices) {
		return 400.0*Math.sin(((index*2*Math.PI)/numOfVertices));
	}
	/**
	 * Returns the y coordinate of the vertex based on its location in the polygon formation
	 * @param index the vertex number of this vertex
	 * @param numOfVertices the total number of vertices
	 * @return the y coordinate of the vertex's position
	 */
	private double getY(double index, double numOfVertices) {
		return 400.0*Math.cos(((index*2*Math.PI)/numOfVertices));
	}
	/**
	 * Adds the specified edge if not already in the graph
	 */
	private void addEdgeIfNeeded() {
		int count = 0;
		String start = "";
		String end = "";
		for(Vertex v : vertexs) {
			if(v.beenClicked()) {
				if(count == 0) {
					start = v.getLabel();
					count++;
				} else {
					end = v.getLabel();
				}
			}
		}
		graph.addEdge(start, end, 5);
	}
}