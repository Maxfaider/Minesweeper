package io.amecodelabs.Minesweeper.game;

import io.amecodelabs.Minesweeper.game.error.InvalidCellError;
import io.amecodelabs.Minesweeper.game.error.MarkerCellError;
import io.amecodelabs.Minesweeper.game.error.VisitedCellError;
import io.amecodelabs.Minesweeper.game.events.LoserListener;
import io.amecodelabs.Minesweeper.game.events.WinnerListener;

public abstract class Board {
	Cell[][] cells;
	WinnerListener winnerListener;
	LoserListener loserListener;
	//default handle
	InvalidCellError invalidCellError = (ex) -> {/* do nothing */};
	MarkerCellError markerCellError = (ex) -> {};
	VisitedCellError visitedCellError = (ex) -> {};
	BoardObserver boardObserver;
	
	abstract protected void createGame();
	
	public Board(Cell[][] cells, WinnerListener winnerListener, LoserListener loserListener) {
		this.cells = cells;
		this.winnerListener = winnerListener;
		this.loserListener = loserListener;
		createGame();
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
}
