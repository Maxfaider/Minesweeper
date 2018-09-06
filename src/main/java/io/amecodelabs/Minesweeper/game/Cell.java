package io.amecodelabs.Minesweeper.game;

public interface Cell {
	boolean isMine();
	void marker();
	void unmarker();
	boolean isMarker();
	void changedVisited();
	boolean isVisited();
	RowAndColumn getPosition();
	int nroMinesAround();
}
