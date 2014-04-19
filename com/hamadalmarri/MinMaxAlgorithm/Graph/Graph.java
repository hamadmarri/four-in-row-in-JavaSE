/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamadalmarri.MinMaxAlgorithm.Graph;

import java.util.LinkedList;

/**
 * 
 * @author hamadalmarri
 */
public final class Graph {

	LinkedList<GraphNode> graphNodes = new LinkedList<GraphNode>();

	public Graph() {
	}

	public void addGraphNode(GraphNode graphNode) {
		this.graphNodes.add(graphNode);
	}

	public void addEdge(GraphNode A, GraphNode B) {
		// set child graph node's parent to current graph node
		B.parent = A;

		// set child graph node's depth
		B.depth = (short) (A.depth + 1);

		// add B as successor of A
		A.edges.addLast(B);
	}

	public GraphNode getFirstGraphNode() {
		return this.graphNodes.get(0);
	}

	public long getNodeNumbers() {
		return this.graphNodes.size();
	}
}
