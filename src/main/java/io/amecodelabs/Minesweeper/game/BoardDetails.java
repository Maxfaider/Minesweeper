package io.amecodelabs.Minesweeper.game;

class BoardDetails {
	private final int rowsTotal;
	private final int columnsTotal;
	private final int num_cells;
	private final int num_cells_to_Win;
	private final int mines;
	
	BoardDetails(int rowsTotal, int columnsTotal, int num_cells, int mines, int num_cells_to_Win) {
		this.rowsTotal = rowsTotal;
		this.columnsTotal = columnsTotal;
		this.num_cells = num_cells;
		this.mines = mines;
		this.num_cells_to_Win = num_cells_to_Win;
	}

	public int getRowsTotal() {
		return rowsTotal;
	}

	public int getColumnsTotal() {
		return columnsTotal;
	}

	public int getNum_cells() {
		return num_cells;
	}

	public int getNum_cells_to_Win() {
		return num_cells_to_Win;
	}
	
	public int getMines() {
		return mines;
	}
}
