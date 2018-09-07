package io.amecodelabs.Minesweeper;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import io.amecodelabs.Minesweeper.game.Board;
import io.amecodelabs.Minesweeper.game.BoardBuilder;
import io.amecodelabs.Minesweeper.game.Cell;
import io.amecodelabs.Minesweeper.game.MinesWeeperException;

@RunWith(value = Parameterized.class)
public class AppTest {
	Logger log = Logger.getLogger(AppTest.class.getName());
	private Board board;
	private final int row;
	private final int column;
	private final int mines_expected;
	
	@Parameters
	public static Iterable<Integer[]> getMines() {
		return Arrays.asList(new Integer[][] {
	         { 10, 10, 60 },
	         { 10, 8, 58 },
	         { 9, 9, 20},
	         { 16, 16, 40},
	         { 16, 30, 99},
	         {12, 4, 1},
	         {10, 3, 0},
	         {10, 10, 100}
	      });
	}
	
	public AppTest(Integer row, Integer column, Integer mines_expected) {
		this.row = row;
		this.column = column;
		this.mines_expected = mines_expected;
	}
	
	@Before
	public void buildBoard() {
		try {
			board = createBoard(row, column, mines_expected);
		} catch (MinesWeeperException e) {
			log.log(Level.SEVERE, e.getErrorMotive());
		}
	}
	
	@Test
    public void testMinesExpected() {
    	Cell[][] cells = board.getCells();
    	int countMines = 0;
    	
    	for(int i = 0; i < board.getRowTotal(); i++) 
			for(int j = 0; j< board.getColumnTotal(); j++) 
				if(cells[i][j].isMine())
					++countMines;
		
    	Assert.assertEquals(mines_expected, countMines);
    }
	
	public Board createBoard(int rows, int columns, int mines) throws MinesWeeperException {
		Board board = BoardBuilder.createGame(rows, columns, mines)
				.setWinnerListener((b) -> log.info("event winner trigger"))
				.setLoserListener((b) -> log.info("event loser trigger"))
				.setHandleInvalidCellError((ex) -> log.info(ex.getMessage()))
				.setHandleMarkerCellError((ex) -> log.info(ex.getMessage()))
				.setHandleVisitedCellError((ex) -> log.info(ex.getMessage()))
				.setObserver((b, cell) -> log.info(""+cell.isMarker()))
				.build();
		
		return board;
	}
}
