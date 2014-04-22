package com.hamadalmarri.MinMaxAlgorithm.FourInRowPuzzle;

/**
 * @author Hamad Almarri <hamad.s.almarri@gmail.com>
 * 
 */
public class PuzzleHole {
	private PUZZLE_HOLE_COLORS color;



	public PuzzleHole() {
		this.color = PUZZLE_HOLE_COLORS.NO_COLOR;
	}



	public PuzzleHole(PuzzleHole B) {
		this.color = B.color;
	}



	public boolean isEmpty() {
		return (this.color == PUZZLE_HOLE_COLORS.NO_COLOR);
	}



	public boolean isRed() {
		return (this.color == PUZZLE_HOLE_COLORS.RED);

	}



	public boolean isBlack() {
		return (this.color == PUZZLE_HOLE_COLORS.BLACK);

	}



	public PUZZLE_HOLE_COLORS getColor() {
		return color;
	}



	public void setColor(PUZZLE_HOLE_COLORS color) {
		this.color = color;
	}



	@Override
	public String toString() {
		if (this.isRed())
			return "O";
		else if (this.isBlack())
			return "X";

		return " ";
	}

}
