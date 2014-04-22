/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamadalmarri.MinMaxAlgorithm.Graph;

import java.util.LinkedList;


/**
 * @author Hamad Almarri <hamad.s.almarri@gmail.com>
 *
 */
public final class GraphNode {

	public LinkedList<GraphNode> edges = new LinkedList<GraphNode>();

	// graph node data is the token position
	public byte tokenPosition = 7;

	// the depth of the graph node
	public byte depth = 0;

	// MinMax Value
	public short minMaxValue = Short.MIN_VALUE;

	// parent node
	public GraphNode parent = null;



	public GraphNode() {
	}



	void addEdge(GraphNode child) {
		this.edges.addLast(child);
	}
}
