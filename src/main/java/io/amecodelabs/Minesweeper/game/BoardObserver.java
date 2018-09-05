package io.amecodelabs.Minesweeper.game;

public interface BoardObserver {
	void onBoardValueChanged(Board board, Cell cell);
}
