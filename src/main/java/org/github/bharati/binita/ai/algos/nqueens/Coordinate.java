package org.github.bharati.binita.ai.algos.nqueens;

/**
 * 
 * @author binita.bharati@gmail.com 
 * 		   Class representing any coordinate on the Chess board.
 */

public class Coordinate {
	private Integer x;
	private Integer y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "{ X = " + x + "; Y = " + y + " }";
	}

	@Override
	public boolean equals(Object obj) {
		Coordinate input = (Coordinate) obj;
		if (input.getX() == this.getX() && input.getY() == this.getY()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x.hashCode() + y.hashCode();
	}

}
