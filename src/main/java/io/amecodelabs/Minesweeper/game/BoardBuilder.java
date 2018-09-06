package io.amecodelabs.Minesweeper.game;

import java.util.logging.Logger;

import io.amecodelabs.Minesweeper.game.error.InvalidCellError;
import io.amecodelabs.Minesweeper.game.error.MarkerCellError;
import io.amecodelabs.Minesweeper.game.error.VisitedCellError;
import io.amecodelabs.Minesweeper.game.events.LoserListener;
import io.amecodelabs.Minesweeper.game.events.WinnerListener;

public class BoardBuilder {
	private Logger log = Logger.getLogger(this.getClass().getName());
	private WinnerListener winnerListener;
	private LoserListener loserListener;
	private InvalidCellError invalidCellError;
	private MarkerCellError markerCellError;
	private VisitedCellError visitedCellError;
	private BoardObserver boardObserver;
	private BoardDetails boardDetails;
	
	public static BoardBuilder createGame(int rows, int columns, int mines) {
		int num_cell = rows * columns;
		int num_cell_to_Win = num_cell - mines;
		return new BoardBuilder(new BoardDetails(rows, columns, num_cell, mines, num_cell_to_Win));
	}
	
	private BoardBuilder(BoardDetails boardDetails) {
		this.boardDetails = boardDetails;
	}
	
	public Board build() throws MinesWeeperException {
		checkEvents(); //tirgger Exception
		
		Cell[][] cells = new Cell[this.boardDetails.getRowsTotal()][this.boardDetails.getColumnsTotal()];
		initializeCells(cells);
		insertMines(cells);
		insertValuesMinesAround(cells);

		Board board = new Board(cells, this.boardDetails, this.winnerListener, this.loserListener);
		insertHandleError(board);
		insertObserver(board);
		return board;
	}
	
	private void initializeCells(Cell[][] cells) {
		for(int i = 0; i < cells.length; i++) 
			for(int j = 0; j < cells[i].length; j++)
				cells[i][j] = new Cell_Impl(new RowAndColumn(i, j));
		log.info("initialize cells");
	}
	
	private void insertMines(Cell[][] cells) {
		int rowRandom;
		int columnRandom;
		for(int i=1; i<=this.boardDetails.getMines(); i++) {
			do {
				rowRandom = (int)(Math.random()*this.boardDetails.getRowsTotal());
				columnRandom = (int)(Math.random()*this.boardDetails.getColumnsTotal());
			} while(cells[rowRandom][columnRandom].isMine());
			((Cell_Impl) cells[rowRandom][columnRandom]).addMine();
		}
		log.info("Insert Mines");
	}
	
	private void insertValuesMinesAround(Cell[][] cells) {
		for(int i = 0; i < cells.length; i++) 
			for(int j = 0; j < cells[i].length; j++)
				countMinesAround(cells, i, j);
		log.info("insert Values Mines Around");
	}
	
	private void countMinesAround(Cell[][] cells, int externalIndex, int innerIndex) {	
		int countMines = 0;
		for(int i = externalIndex-1; i <= externalIndex+1; i++) {
			for(int j = innerIndex-1; j<= innerIndex+1; j++) {
				if( (i>=0 && i<this.boardDetails.getRowsTotal()) && (j>=0 && j<this.boardDetails.getColumnsTotal()) )
					if(cells[i][j].isMine())
						countMines++;
			}
		}
		if(cells[externalIndex][innerIndex].isMine()) 
			countMines--; 
		((Cell_Impl) cells[externalIndex][innerIndex]).setMinesAround(countMines);
	}
	
	private void checkEvents() throws MinesWeeperException {
		if( this.winnerListener == null || this.loserListener == null ) {
			throw new MinesWeeperException("WinnerListener or LoserListener not exists");
		}
		log.info("check Events");
	}
	
	private void insertHandleError(Board board) {
		if(this.invalidCellError != null)
			board.setHandleInvalidCellError(this.invalidCellError);
		if(this.markerCellError != null)
			board.setHandleMarkerCellError(this.markerCellError);
		if(this.visitedCellError != null)
			board.setHandleVisitedCellError(this.visitedCellError);
		log.info("insert Handle Error");
	}
	
	private void insertObserver(Board board) {
		if(this.boardObserver != null)
			board.setObserver(boardObserver);
		log.info("insert Observer");
	}
	
	public BoardBuilder setHandleInvalidCellError(InvalidCellError invalidCellError) {
		this.invalidCellError = invalidCellError;
		return this;
	}
	
	public BoardBuilder setHandleMarkerCellError(MarkerCellError markerCellError) {
		this.markerCellError = markerCellError;
		return this;
	}
	
	public BoardBuilder setHandleVisitedCellError(VisitedCellError visitedCellError) {
		this.visitedCellError = visitedCellError;
		return this;
	}
	
	public BoardBuilder setObserver(BoardObserver boardObserver) {
		this.boardObserver = boardObserver;
		return this;
	}

	public BoardBuilder setWinnerListener(WinnerListener winnerListener) {
		this.winnerListener = winnerListener;
		return this;
	}

	public BoardBuilder setLoserListener(LoserListener loserListener) {
		this.loserListener = loserListener;
		return this;
	}
}
