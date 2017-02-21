package com.saimaddhi.graph;
/**
 * Driver class
 * @author saimaddhi
 *
 */
public class Driver {
	public static void main(String[] args) {
		Graph graph = new Graph();
		//graph.addEdgesFromFile("Edges");
		/*
		 * The UI Program can be constructed with a graph. The program has features that include showing the minimum
		 * spanning tree, adding a vertex, and adding an edge as well.
		 */
		graph.addVertex("V1");
		Grapher dg = new Grapher(graph);
	}
}
