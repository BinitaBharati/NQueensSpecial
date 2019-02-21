package org.github.bharati.binita.ai.algos.nqueens;

import java.util.List;

/**
 * 
 * @author binita.bharati@gmail.com Class representing a line between two or
 *         more points on the Chess board.
 */

public class Line {

	private List<Coordinate> memberCoordinate;
	private Double slope;

	public Line(List<Coordinate> memberCoordinate, double slope) {
		this.memberCoordinate = memberCoordinate;
		this.slope = slope;
	}

	public List<Coordinate> getMemberCoordinate() {
		return memberCoordinate;
	}

	public void setMemberCoordinate(List<Coordinate> memberCoordinate) {
		this.memberCoordinate = memberCoordinate;
	}

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public void addCoordinate(Coordinate input) {
		this.getMemberCoordinate().add(input);
	}

	@Override
	public boolean equals(Object obj) {
		Line input = (Line) obj;
		if (this.getMemberCoordinate().contains(input.getMemberCoordinate().get(0))) {
			if (this.getSlope() == input.getSlope()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return slope.hashCode();
	}

	@Override
	public String toString() {
		return "Member coordinates = " + memberCoordinate + ", slope = " + slope;
	}
}
