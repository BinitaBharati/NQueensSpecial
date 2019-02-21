package org.github.bharati.binita.ai.algos.nqueens;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * 
 * @author binita.bharati@gmail.com
 * This test will populate the special N queen condition, and verifies the validity
 *
 */

public class NQueenSpecialTest {

	@Test
	public void test() {
		NQueensSpecial instance = new NQueensSpecial(8);
		//We can put any arbitary step between 0 and 7.
		instance.navigate(4);
		List<Coordinate> queenPositions = instance.getQueenPositions();

		assertTrue(arePositionsNQueenSpecialCompliant(queenPositions));
	}

	private boolean arePositionsNQueenSpecialCompliant(List<Coordinate> queenPositions) {
		// No two coordinates should have same row
		final Set<Integer> tmpSet = new HashSet<>();
		List<Coordinate> duplicateRows = queenPositions.stream().filter(eachCoord -> {
			return !tmpSet.add(eachCoord.getX());
		}).collect(Collectors.toList());
		if (duplicateRows.size() > 0) {
			return false;
		}

		// No two coordinates should have same column
		final Set tmpSet1 = new HashSet<>();
		List<Coordinate> duplicateCols = queenPositions.stream().filter(eachCoord -> {
			return !tmpSet1.add(eachCoord.getY());
		}).collect(Collectors.toList());
		if (duplicateCols.size() > 0) {
			return false;
		}

		// No two coordinates should have slope == 1; and no 3 or more coordinates should form a straight line at any slope
		List<Line> linesPassingInput = null;
		for (int j = 0; j < queenPositions.size(); j++) {
			linesPassingInput = new ArrayList<>();
			Coordinate input = queenPositions.get(j);

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
										// coordListItr.add(tmp);
										return false;
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
		}

		return true;
	}

	private double getSlope(Coordinate coord1, Coordinate coord2) {
		if (coord1.getX() == coord2.getX()) {
			return -1;
		}
		double slope = ((double) (coord1.getY() - coord2.getY())) / (coord1.getX() - coord2.getX());
		return Math.abs(slope);
	}

}
