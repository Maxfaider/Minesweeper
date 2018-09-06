package io.amecodelabs.Minesweeper.game;

import java.util.logging.Logger;

import io.amecodelabs.Minesweeper.game.error.InvalidCellError;
import io.amecodelabs.Minesweeper.game.error.MarkerCellError;
import io.amecodelabs.Minesweeper.game.error.VisitedCellError;
import io.amecodelabs.Minesweeper.game.events.LoserListener;
import io.amecodelabs.Minesweeper.game.events.WinnerListener;

public class Board {
	private Logger log = Logger.getLogger(this.getClass().getName());
	private final Cell[][] cells;
	private int num_cells_visited;
	private WinnerListener winnerListener;
	private LoserListener loserListener;
	//default handle
	private InvalidCellError invalidCellError = (ex) -> {/* do nothing */};
	private MarkerCellError markerCellError = (ex) -> {};
	private VisitedCellError visitedCellError = (ex) -> {};
	private BoardObserver boardObserver;
	private final BoardDetails boardDetails;
	
	Board(Cell[][] cells, BoardDetails boardDetails, WinnerListener winnerListener, LoserListener loserListener) {
		this.cells = cells;
		this.boardDetails = boardDetails;
		this.winnerListener = winnerListener;
		this.loserListener = loserListener;
		this.num_cells_visited = 0;
	}
	
	public void markCell(int row, int column) {
		if(invalidCell(row, column))
			return;
		if(markerCellError(row, column))
			return;
		if(visitedCellError(row, column))
			return;
		this.cells[row][column].marker();
	}
	
	public void unMarkCell(int row, int column) {
		if(invalidCell(row, column))
			return;
		if(visitedCellError(row, column))
			return;
		this.cells[row][column].unmarker();
	}
	
	public void play(int row, int column) {
		if(invalidCell(row, column))
			return;
		if(visitedCellError(row, column))
			return;
		if(this.cells[row][column].isMine()) { 
			this.loserListener.accept(this); 
			return;
		}
		
		if(this.cells[row][column].nroMinesAround() > 0) {
			visitedCell(this.cells[row][column]);
		} else  {
			uncoverCells(row, column);
		}
		
	}
	
	private void visitedCell(Cell cell) {
		cell.changedVisited(); 
		++this.num_cells_visited;
		if(this.boardDetails.getNum_cells_to_Win() == this.num_cells_visited) 
			this.winnerListener.accept(this);
	}
	
	private void uncoverCells(int currentRow, int currentColumn) {	
		visitedCell(this.cells[currentRow][currentColumn]);
		for(int i = currentRow-1; i <= currentRow+1; i++) 
			for(int j = currentColumn-1; j<= currentColumn+1; j++) 
				if( (i>=0 && i<this.boardDetails.getRowsTotal()) && (j>=0 && j<this.boardDetails.getColumnsTotal()))
					uncoverCell(this.cells[i][j]);
	}
	
	private void uncoverCell(Cell cell) {
		//System.out.println(cell.getPosition().getRow() + " a "+ cell.getPosition().getColumn());
		if(cell.isVisited() || cell.isMine() || cell.isMarker())
			return;
		if(cell.nroMinesAround() > 0) {
			visitedCell(cell);
		} else {
			uncoverCells(cell.getPosition().getRow(), cell.getPosition().getColumn());
		}	
	}
	
	private boolean invalidCell(int row, int column) {
		if( (row<0 && row>=this.boardDetails.getRowsTotal()) || (column<0 && column>=this.boardDetails.getColumnsTotal()) ) {
			this.invalidCellError.accept(
					new MinesWeeperException(InvalidCellError.code, "Row or Column Invalid", row + " | " + column)
			);
			return true;
		}
		return false;
	}
	
	private boolean markerCellError(int row, int column) {
		if( this.cells[row][column].isMarker() ) {
			this.markerCellError.accept(
					new MinesWeeperException(MarkerCellError.code, "Cell is already marked")
			);
			return true;
		}
		return false;	
	}
	
	private boolean visitedCellError(int row, int column) {
		if( this.cells[row][column].isVisited() ) {
			this.visitedCellError.accept(
					new MinesWeeperException(VisitedCellError.code, "Cell was already visited")
			);
			return true;
		}
		return false;
	}
	
	public void setHandleInvalidCellError(InvalidCellError invalidCellError) {
		this.invalidCellError = invalidCellError;
	}
	
	public void setHandleMarkerCellError(MarkerCellError markerCellError) {
		this.markerCellError = markerCellError;
	}
	
	public void setHandleVisitedCellError(VisitedCellError visitedCellError) {
		this.visitedCellError = visitedCellError;
	}
	
	public void setObserver(BoardObserver boardObserver) {
		this.boardObserver = boardObserver;
	}
	
	public Cell[][] getCells() {
		return this.cells;
	}

	public WinnerListener getWinnerListener() {
		return winnerListener;
	}

	public LoserListener getLoserListener() {
		return loserListener;
	}

	public InvalidCellError getInvalidCellError() {
		return invalidCellError;
	}

	public MarkerCellError getMarkerCellError() {
		return markerCellError;
	}

	public VisitedCellError getVisitedCellError() {
		return visitedCellError;
	}

	public BoardObserver getBoardObserver() {
		return boardObserver;
	}
	
	public int getNum_cells_visited() {
		return num_cells_visited;
	}
	
	public int getRowTotal() {
		return this.boardDetails.getRowsTotal();
	}

	public int getColumnTotal() {
		return this.boardDetails.getColumnsTotal();
	}

	public int getNum_cells() {
		return this.boardDetails.getNum_cells();
	}

	public int getNum_Cell_to_Win() {
		return this.boardDetails.getNum_cells_to_Win();
	}
	
	public int getMines() {
		return this.boardDetails.getMines();
	}
}
