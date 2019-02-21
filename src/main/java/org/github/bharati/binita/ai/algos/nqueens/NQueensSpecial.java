package org.github.bharati.binita.ai.algos.nqueens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 
 * @author binita.bharati@gmail.com This is a special implementation of NQueens
 *         problem, with additional condition that no 3 or more Queen
 *         coordinates should form a line at any angle across the chess board.
 * 
 *         This code is a implementation of "Constraint Satisfaction Problems" algorithm
 *         described in "Artificial Intelligence - A modern approach" book.
 *         Reference : http://aima.cs.berkeley.edu/
 *
 */

public class NQueensSpecial {

	private static int size;
	private static List<Coordinate> queenPositions;

	public NQueensSpecial(int size) {
		this.size = size;
		queenPositions = new ArrayList<>();
	}

	public static List<Coordinate> getQueenPositions() {
		return queenPositions;
	}

	public static void setQueenPositions(List<Coordinate> queenPositions) {
		NQueensSpecial.queenPositions = queenPositions;
	}

	private boolean isQueenUnderAttack() {
		for (int i = 0; i < queenPositions.size(); i++) {
			Coordinate tmp = queenPositions.get(i);
			if (getQueenConflictCount(tmp) != 0) {
				return true;
			}
		}
		return false;
	}

	private double getSlope(Coordinate coord1, Coordinate coord2) {
		if (coord1.getX() == coord2.getX()) {
			return -1;
		}
		double slope = ((double) (coord1.getY() - coord2.getY())) / (coord1.getX() - coord2.getX());
		return Math.abs(slope);
	}

	private int getAdditionalConflictCount(Coordinate input) {

		int conflictCount = 0;
		List<Line> linesPassingInput = new ArrayList<>();
		for (int i = 0; i < queenPositions.size(); i++) {
			Coordinate tmp = queenPositions.get(i);
			if (!input.equals(tmp)) {
				double slope = getSlope(input, tmp);
				if (slope != 1) {
					List<Coordinate> coordList = new ArrayList<>();
					coordList.add(input);
					Line testLine = new Line(coordList, slope);
					int idx = linesPassingInput.indexOf(testLine);
					if (idx != -1) {
						Line line1 = linesPassingInput.get(idx);
						List<Coordinate> memCoordList = line1.getMemberCoordinate();
						ListIterator<Coordinate> coordListItr = memCoordList.listIterator();

						while (coordListItr.hasNext()) {
							Coordinate eachCoord = coordListItr.next();
							if (!eachCoord.equals(input)) {
								double slope1 = getSlope(tmp, eachCoord);
								if (slope1 == slope) {
									// line1.getMemberCoordinate().add(tmp);
									coordListItr.add(tmp);
								} else {
									coordList = new ArrayList<>();
									coordList.add(input);
									coordList.add(tmp);
									Line newLine = new Line(coordList, slope);
									linesPassingInput.add(newLine);
								}
							}
						}
					} else {
						coordList = new ArrayList<>();
						coordList.add(input);
						coordList.add(tmp);
						Line newLine = new Line(coordList, slope);
						linesPassingInput.add(newLine);
					}
				}

			}
		}
		// Now, check all lines passing through the input containing more than 2 entries
		List<Line> violatingCoordList = linesPassingInput.stream().filter(eachLine -> {
			return eachLine.getMemberCoordinate().size() >= 3;
		}).collect(Collectors.toList());
		System.out.println("violatingCoordList = " + violatingCoordList + "for input = " + input);
		for (Line line : violatingCoordList) {
			conflictCount = conflictCount + line.getMemberCoordinate().size() - 2;
		}
		System.out.println("additional confict count = " + conflictCount);
		return conflictCount;
	}

	private int getQueenConflictCount(Coordinate input) {
		// lines.clear();
		int conflictCount = getAdditionalConflictCount(input);
		Map<Double, Integer> slopeToCoordCount = new HashMap<Double, Integer>();
		for (int i = 0; i < queenPositions.size(); i++) {
			Coordinate temp = queenPositions.get(i);
			if (!temp.equals(input)) {
				if (temp.getY() == input.getY()) {
					conflictCount++;
					System.out.println("conflictCount : column conflict for " + input.getX() + " " + input.getY());
				} else {
					double slope = getSlope(temp, input);
					if (slope == 1) {
						conflictCount++;
						System.out.println("conflictCount : diagonal conflict for " + input.getX() + " " + input.getY()
								+ " with " + temp);

					}
				}
			}
		}
		return conflictCount;
	}

	/**
	 * Assign queens to columns as given by input step. If column index exceeds
	 * Chess board size roll over to beginning.
	 * 
	 * @param step
	 */
	public void assign(int step) {
		boolean allFilled = false;
		int row = 0;
		int colIdx = step;

		while (!allFilled) {
			if (colIdx >= size) {
				colIdx = step;

			}
			queenPositions.add(new Coordinate(row, colIdx));
			colIdx = colIdx + step;
			row++;
			if (row >= size) {
				allFilled = true;
			}
		}

	}

	private void resolveConflicts(Coordinate input) {
		System.out.println("resolveConflicts: entered with input " + input);
		int conflictCount = getQueenConflictCount(input);
		if (conflictCount == 0) {
			return;
		}
		// extrapolate and find the colIdx for input where conflicts will be minimal.
		int y = 0;
		int minimumConflicts = Integer.MAX_VALUE;
		for (int colIdx = 0; colIdx < size; colIdx++) {
			if (colIdx != input.getY()) {
				Coordinate temp = new Coordinate(input.getX(), colIdx);
				int tempConflict = getQueenConflictCount(temp);
				System.out.println("X = " + input.getX() + " Y = " + colIdx + ", tempConflicts = " + tempConflict);
				if (tempConflict < minimumConflicts) {
					minimumConflicts = tempConflict;
					y = colIdx;
				}
			}
		}
		System.out.println("X = " + input.getX() + " Y = " + y + " after resolution minimal conflicts = " + minimumConflicts);
		// You have the column position of minimal conflicts.
		input.setY(y);
	}

	public void navigate(int step) {
		assign(step);
		System.out.println("original queen positions : " + queenPositions);
		while (isQueenUnderAttack()) {
			for (int i = 0; i < queenPositions.size(); i++) {
				resolveConflicts(queenPositions.get(i));
			}

		}
	}

	public static void main(String[] args) {
		NQueensSpecial instance = new NQueensSpecial(8);
		instance.navigate(3);
		System.out.println("Resolved queen positions : " + queenPositions);
	}

}
