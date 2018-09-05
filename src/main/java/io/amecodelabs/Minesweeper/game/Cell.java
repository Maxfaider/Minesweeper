package io.amecodelabs.Minesweeper.game;

public interface Cell {
	boolean isMine();
	
	void setMarker(boolean marker);
	boolean isMarker();
	
	void changedVisited();
	boolean isVisited();
	
	RowAndColumn getPosition();
	
	int nroMinesAround();
}
