package com.hamadalmarri.MinMaxAlgorithm;

import java.io.IOException;
import java.util.Scanner;

import com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle.FourInRowPuzzle;
import com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle.FourInRowPuzzle.FullColumn;
import com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle.Game;
import com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle.PUZZLE_HOLE_COLORS;
import com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle.PLAYERS;


/**
 * @author Hamad Almarri <hamad.s.almarri@gmail.com>
 *
 */
public class MinMaxAlgorithm {

	private static void StartGame() throws IOException {
		FourInRowPuzzle p = new FourInRowPuzzle();
		Game game = new Game();
		Scanner in = new Scanner(System.in);

		boolean player = false;

		PUZZLE_HOLE_COLORS currentColor;
		short column = 0;
		short maxDepth;

		long startTime, endTime;

		System.out.print("0: you play first, 1 computer play first: ");
		player = (in.nextByte() != 0);

		System.out.print("enter max depth: ");
		maxDepth = in.nextShort();

		game.SetMaxDepth((byte) maxDepth);

		System.out.println(p.toString());

		while (!p.isFull() && !p.isDone()) {
			try {
				if (player) {
					currentColor = PLAYERS.PLAYER1;
					game.SetPuzzle(p);
					System.out.println("computer is thinking ...");
					startTime = System.currentTimeMillis();
					column = game.GetNextPlay();
					endTime = System.currentTimeMillis();
					System.out.println("computer played: " + column);
					System.out.println("Created Nodes: " + game.GetGraphNodesCount());
					System.out.println("took(" + (float) (endTime - startTime) / 1000 + "s)");
				} else {
					currentColor = PLAYERS.PLAYER2;
					System.out.print("your turn, type a column number: ");
					column = in.nextShort();
				}

				p.addToken(currentColor, (byte) column);

				System.out.println(p.toString());

				player = !player;

			} catch (FullColumn e) {
				System.out.println("column is full!");
			}
		}

		System.out.println(p.toString());

		if (p.isDone()) {
			if (p.getWinner() == PUZZLE_HOLE_COLORS.RED)
				System.out.println("Red won!");
			else if (p.getWinner() == PUZZLE_HOLE_COLORS.BLACK)
				System.out.println("Black won!");
		} else {
			System.out.println("No body won!");
		}

		System.out.println("");
	}



	public static void main(String[] args) throws IOException {
		StartGame();
	}

}
