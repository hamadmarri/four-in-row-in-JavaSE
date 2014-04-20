package com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle;

import java.util.ArrayDeque;

import com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle.FourInRowPuzzle.FullColumn;
import com.hamadalmarri.MinMaxAlgorithm.Graph.Graph;
import com.hamadalmarri.MinMaxAlgorithm.Graph.GraphNode;


class SPECIAL_VALUES {
	public static final short WIN_VALUE = 32767;
	public static final short LOSS_VALUE = -32767;
	public static final short NOT_ASSIGNED = -32768;
}

/**
 * @author hamadalmarri
 * 
 */
public final class Game {
	// original puzzle for comparision
	private FourInRowPuzzle originalPuzzle;

	// a FourInRowPuzzle instance
	private FourInRowPuzzle aPuzzle;

	// maximum depth for DFS
	private short maxDepth;

	// graph ADT
	private Graph aGraph;

	// graph node
	private GraphNode aGraphNode;

	// stack
	private ArrayDeque<GraphNode> aStack = new ArrayDeque<GraphNode>();

	public Game() {
		// default is 5
		this.maxDepth = 5;
	}

	// SetPuzzle to be solved
	public void SetPuzzle(FourInRowPuzzle aPuzzle) {
		this.aPuzzle = new FourInRowPuzzle(aPuzzle);
		this.originalPuzzle = new FourInRowPuzzle(aPuzzle);
	}

	// setting the max depth for DFS
	public void SetMaxDepth(short maxDepth) {
		this.maxDepth = maxDepth;
	}

	// get the next suggested play
	public short GetNextPlay() throws FullColumn {

		// allocate a new graph
		this.aGraph = new Graph();

		// call DepthFirstSearch function
		return DepthFirstSearch();
	}

	// the depth first search algorithm
	private short DepthFirstSearch() throws FullColumn {

		// create first node
		this.aGraphNode = new GraphNode();

		// add it in the graph
		this.aGraph.addGraphNode(this.aGraphNode);

		// push it to stack
		this.aStack.push(this.aGraphNode);

		// while stack is not empty
		while (!this.aStack.isEmpty()) {

			// get the top graphNode from stack
			this.aGraphNode = this.aStack.peek();

			// if it has no children and
			// not reach the maxDepth and not full, then develop children
			if (this.aGraphNode.edges.isEmpty()
					&& this.aGraphNode.depth < maxDepth
					&& !this.aPuzzle.isFull()) {

				// apply the move of this current aGraphNode
				ApplyMove();

				// check if it's not done yet
				if (!this.aPuzzle.isDone()) {

					// check if need to develop children based on Alpha-Beta
					// Alorithm
					if (IsNeedToDevelopChildren())
						// develop children
						DevelopChildren();
					else
						PopOutOfStackAndRevertMove();

					// else if done
				} else {
					// just calculate the minMaxValue
					CalculateMinMaxValue();

					PopOutOfStackAndRevertMove();
				}
			}

			// else if it has no children and
			// reach the maxDepth (i.e. leaf node), then calculate minMaxValue
			else if (this.aGraphNode.edges.isEmpty()
					&& this.aGraphNode.depth >= maxDepth) {

				// apply the move of this current aGraphNode
				ApplyMove();

				// check if it's done
				this.aPuzzle.isDone();

				// calculate minMaxValue
				CalculateMinMaxValue();

				PopOutOfStackAndRevertMove();
			}

			// else if it has children and Depth is even, get Max child value
			else if (!this.aGraphNode.edges.isEmpty()
					&& (this.aGraphNode.depth % 2) == 0) {

				// get Max child value
				this.aGraphNode.minMaxValue = GetMaxChildValue(this.aGraphNode);

				PopOutOfStackAndRevertMove();
			}

			// else if it has children and Depth is odd, get Min child value
			else if (!this.aGraphNode.edges.isEmpty()
					&& (this.aGraphNode.depth % 2) == 1) {

				// get Min child value
				this.aGraphNode.minMaxValue = GetMinChildValue(this.aGraphNode);

				PopOutOfStackAndRevertMove();
			}

		} // end while

		// get the right play from root's children
		for (GraphNode g : this.aGraphNode.edges) {
			if (this.aGraphNode.minMaxValue == g.minMaxValue) {
				return g.tokenPosition;
			}
		}

		return this.aGraphNode.tokenPosition;
	}

	// apply move
	private void ApplyMove() throws FullColumn {
		// check if valid play has been played
		if (this.aGraphNode.tokenPosition < 7) {
			// check if player1 has been played
			if ((this.aGraphNode.depth % 2) == 1)
				this.aPuzzle.addToken(PLAYERS.PLAYER1,
						this.aGraphNode.tokenPosition);
			// or player2
			else
				this.aPuzzle.addToken(PLAYERS.PLAYER2,
						this.aGraphNode.tokenPosition);
		}
	}

	// revert back the move
	private void RevertBackMove() {
		// check if valid play has been played
		if (this.aGraphNode.tokenPosition < 7)
			this.aPuzzle.removeToken(this.aGraphNode.tokenPosition);

	}

	/*
	 * private void GetTopFromStackAndApplyMove() throws FullColumn { // get the
	 * top graphNode from stack this.aGraphNode = this.aStack.peek();
	 * 
	 * // apply the move of this current aGraphNode ApplyMove(); }
	 */

	private void PopOutOfStackAndRevertMove() {
		// revert back the move
		RevertBackMove();

		// pop the graph node
		this.aStack.pop();
	}

	// is need to develop children based on Alpha-Beta check
	private boolean IsNeedToDevelopChildren() {
		short max = SPECIAL_VALUES.NOT_ASSIGNED;
		short min = SPECIAL_VALUES.NOT_ASSIGNED;

		// check if parent or parents' parent are NULLS, then return true need
		// to develop children
		if (this.aGraphNode.parent == null
				|| this.aGraphNode.parent.parent == null) {
			return true;
		}

		// check if the graph node in max level
		if ((this.aGraphNode.depth % 2) == 0) {
			// get min of brothers
			min = GetMinChildValue(this.aGraphNode.parent);

			// get max of unculs
			max = GetMaxChildValue(this.aGraphNode.parent.parent);
		}
		// else if it is in min level
		else if ((this.aGraphNode.depth % 2) == 1) {
			// get max of brothers
			max = GetMaxChildValue(this.aGraphNode.parent);

			// get min of unculs
			min = GetMinChildValue(this.aGraphNode.parent.parent);
		}

		// if max or min still not assigned, then return true need to develop
		// children
		if (max == SPECIAL_VALUES.NOT_ASSIGNED
				|| min == SPECIAL_VALUES.NOT_ASSIGNED)
			return true;

		// return false if max is greater than min
		return !(max > min);
	}

	// develop children
	private void DevelopChildren() {
		// new graph node child
		GraphNode graphNodeChild;

		// go through all possible next play
		for (short column = 0; column < FourInRowPuzzle.getWidth(); column++) {
			// check if column is not full
			if (!this.aPuzzle.isColumnFull(column)) {

				// create new graph node child
				graphNodeChild = new GraphNode();

				// set the token to be played
				graphNodeChild.tokenPosition = column;

				// add it to graph
				this.aGraph.addGraphNode(graphNodeChild);
				
				// add it to the parent edges
				this.aGraph.addEdge(this.aGraphNode, graphNodeChild);

				// push it to the stack
				this.aStack.push(graphNodeChild);
			} // end if
		} // end for column

	}

	private short GetMaxChildValue(GraphNode aGraphNode) {
		short max = SPECIAL_VALUES.NOT_ASSIGNED;

		for (GraphNode g : aGraphNode.edges) {
			if (g.minMaxValue != SPECIAL_VALUES.NOT_ASSIGNED
					&& g.minMaxValue > max) {
				max = g.minMaxValue;
			}
		}

		return max;
	}

	private short GetMinChildValue(GraphNode aGraphNode) {
		short min = SPECIAL_VALUES.NOT_ASSIGNED;

		for (GraphNode g : aGraphNode.edges) {
			if (g.minMaxValue != SPECIAL_VALUES.NOT_ASSIGNED
					&& (min == SPECIAL_VALUES.NOT_ASSIGNED || g.minMaxValue < min)) {
				min = g.minMaxValue;
			}
		}

		return min;
	}

	// calculate minMaxValue for leaf nodes
	private void CalculateMinMaxValue() {
		// check if player1 won
		if (this.aPuzzle.getWinner() == PLAYERS.PLAYER1)
			// set minMaxValue to win value
			this.aGraphNode.minMaxValue = SPECIAL_VALUES.WIN_VALUE;

		// check if player2 won
		else if (this.aPuzzle.getWinner() == PLAYERS.PLAYER2)
			// set minMaxValue to loss value
			this.aGraphNode.minMaxValue = SPECIAL_VALUES.LOSS_VALUE;

		else
			// count up the sequentials
			this.aGraphNode.minMaxValue = (short) (CountSequentials() + CountCenterColumn());
	}

	// count up the sequentials in the game
	private short CountSequentials() {
		return (short) (CountSequentials(PLAYERS.PLAYER1) - CountSequentials(PLAYERS.PLAYER2));
	}

	// CountSequentials Helper function
	private short CountSequentials(PUZZLE_HOLE_COLORS color) {
		// to hold the results
		short result = 0;

		// go through all holes
		for (short row = 0; row < FourInRowPuzzle.getHieght() - 1; row++) {
			for (short column = 0; column < FourInRowPuzzle.getWidth(); column++) {
				// if this is same color token then count for sequential tokens
				if (this.aPuzzle.holes[row][column].getColor() == color) {

					// check vertically to Upper token
					if (this.aPuzzle.holes[row + 1][column].getColor() == color)
						result += 2;

					// check horizontally to right token
					if (column < FourInRowPuzzle.getWidth() - 1
							&& this.aPuzzle.holes[row][column + 1].getColor() == color)
						result += 2;

					// check diagonally Up-Left
					if (column > 0
							&& this.aPuzzle.holes[row + 1][column - 1]
									.getColor() == color)
						result += 2;

					// check diagonally Up-Right
					if (column < FourInRowPuzzle.getWidth() - 1
							&& this.aPuzzle.holes[row + 1][column + 1]
									.getColor() == color)
						result += 2;
				} // if
			} // for column
		} // for row

		return result;
	}

	private short CountCenterColumn() {
		short result = 0;

		// go through all holes in center column
		for (short row = 0; row < FourInRowPuzzle.getHieght() - 1; row++) {
			if (this.originalPuzzle.holes[row][3].isEmpty()
					&& this.aPuzzle.holes[row][3].getColor() == PLAYERS.PLAYER1)
				result += 10;
			else if (this.originalPuzzle.holes[row][3].isEmpty()
					&& this.aPuzzle.holes[row][3].getColor() == PLAYERS.PLAYER2)
				result -= 10;
		}

		return result;
	}

	// get how many graph nodes have been created
	public long GetGraphNodesCount() {
		return this.aGraph.getNodeNumbers();
	}
}
