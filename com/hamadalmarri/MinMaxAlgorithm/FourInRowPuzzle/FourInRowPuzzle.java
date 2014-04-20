package com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle;

/**
 * @author hamadalmarri
 * 
 */
public class FourInRowPuzzle {
	private final static byte hieght = 6;
	private final static byte width = 7;
	public PuzzleHole[][] holes;
	private int numberOfPlays;
	private PUZZLE_HOLE_COLORS winner;

	public FourInRowPuzzle() {
		this.numberOfPlays = 0;
		this.winner = PUZZLE_HOLE_COLORS.NO_COLOR;
		this.holes = new PuzzleHole[6][7];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				this.holes[i][j] = new PuzzleHole();
			}
		}
	}

	public FourInRowPuzzle(FourInRowPuzzle B) {
		this.holes = new PuzzleHole[6][7];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				this.holes[i][j] = new PuzzleHole();
			}
		}

		for (int row = 0; row < FourInRowPuzzle.hieght; row++) {
			for (int column = 0; column < FourInRowPuzzle.hieght; column++) {
				this.holes[row][column].setColor(B.holes[row][column]
						.getColor());
			}
		}
	}

	public boolean isEmpty() {
		// check bottom row only
		for (short column = 0; column < FourInRowPuzzle.width; column++)
			if (!this.holes[0][column].isEmpty())
				return false;

		return true;
	}

	public boolean isFull() {
		// check higher row only
		for (short column = 0; column < FourInRowPuzzle.width; column++)
			if (this.holes[FourInRowPuzzle.hieght - 1][column].isEmpty())
				return false;

		return true;
	}

	public boolean isColumnFull(short column) {
		return (!this.holes[FourInRowPuzzle.hieght - 1][column].isEmpty());
	}

	public void addToken(PUZZLE_HOLE_COLORS token, short column)
			throws FullColumn {
		// if column is full, throw error
		if (isColumnFull(column)) {
			System.out.println(this);
			throw new FullColumn();
		}

		// to indecate at what level the token should be added
		short row = 0;

		// increment untill find empty hole
		while (!this.holes[row][column].isEmpty())
			row++;

		// set this hole with token color
		this.holes[row][column].setColor(token);

		// increment number of plays
		this.numberOfPlays++;
	}

	void removeToken(short column) {
		// to indecate at what level the token should be removed
		short row = (short) (FourInRowPuzzle.hieght - 1);

		// decrement untill find non empty hole
		while (this.holes[row][column].isEmpty())
			row--;

		// set this hole with no color
		this.holes[row][column].setColor(PUZZLE_HOLE_COLORS.NO_COLOR);

		// decrement number of plays
		this.numberOfPlays--;
	}

	public boolean isDone() {

		// to get the current color
		PUZZLE_HOLE_COLORS currentColor;

		// go through all holes
		for (short row = 0; row < FourInRowPuzzle.hieght; row++) {
			for (short column = 0; column < FourInRowPuzzle.width; column++) {

				// get currentcolor
				currentColor = this.holes[row][column].getColor();

				// if blank hole just continue to the next
				if (currentColor == PUZZLE_HOLE_COLORS.NO_COLOR) {
					continue;
				}

				// check vertically to Upper tokens
				if (row + 3 < FourInRowPuzzle.hieght) {
					if (this.holes[row + 1][column].getColor() == currentColor
							&& this.holes[row + 2][column].getColor() == currentColor
							&& this.holes[row + 3][column].getColor() == currentColor) {

						// set the winner
						this.winner = currentColor;
						return true;
					}
				}

				// check horizontally to right tokens
				if (column + 3 < FourInRowPuzzle.width) {
					if (this.holes[row][column + 1].getColor() == currentColor
							&& this.holes[row][column + 2].getColor() == currentColor
							&& this.holes[row][column + 3].getColor() == currentColor) {

						// set the winner
						this.winner = currentColor;
						return true;
					}
				}

				// check diagonally Up-Left
				if (row + 3 < FourInRowPuzzle.hieght && column - 3 >= 0) {
					if (this.holes[row + 1][column - 1].getColor() == currentColor
							&& this.holes[row + 2][column - 2].getColor() == currentColor
							&& this.holes[row + 3][column - 3].getColor() == currentColor) {

						// set the winner
						this.winner = currentColor;
						return true;
					}
				}

				// check diagonally Up-Right
				if (row + 3 < FourInRowPuzzle.hieght && column + 3 < FourInRowPuzzle.width) {
					if (this.holes[row + 1][column + 1].getColor() == currentColor
							&& this.holes[row + 2][column + 2].getColor() == currentColor
							&& this.holes[row + 3][column + 3].getColor() == currentColor) {

						// set the winner
						this.winner = currentColor;
						return true;
					}
				}
			}
		}

		this.winner = PUZZLE_HOLE_COLORS.NO_COLOR;
		return false;
	}

	public PUZZLE_HOLE_COLORS getWinner() {
		return this.winner;
	}

	@Override
	public String toString() {
		// the string to be returned
		StringBuilder aString = new StringBuilder("");

		for (short row = (short) (FourInRowPuzzle.hieght - 1); row >= 0; row--) {

			// left boundry
			aString.append("| ");

			for (short column = 0; column < FourInRowPuzzle.width; column++) {
				aString.append(this.holes[row][column].toString()).append(" ");
			}

			// right boundry and new line
			aString.append("|\n");
		}

		aString.append("  - - - - - - -\n");
		aString.append("  0 1 2 3 4 5 6\n");

		return aString.toString();
	}

	public static byte getHieght() {
		return FourInRowPuzzle.hieght;
	}

//	public void setHieght(short hieght) {
//		this.hieght = hieght;
//	}

	public static byte getWidth() {
		return FourInRowPuzzle.width;
	}

//	public void setWidth(short width) {
//		this.width = width;
//	}

	public int getNumberOfPlays() {
		return numberOfPlays;
	}

	public void setNumberOfPlays(int numberOfPlays) {
		this.numberOfPlays = numberOfPlays;
	}

	// error classes //
	public static class FullColumn extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 80072811230858264L;
	};

}
