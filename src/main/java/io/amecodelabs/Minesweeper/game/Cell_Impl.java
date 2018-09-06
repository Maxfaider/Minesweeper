package io.amecodelabs.Minesweeper.game;

public class Cell_Impl implements Cell {
	private boolean mine;
	private boolean marked;
	private boolean visited;
	private int nroMinesAround;
	private final RowAndColumn position;
	
	Cell_Impl(RowAndColumn position) {
		this.position = position;
		this.mine = false;
		this.marked = false;
		this.visited = false;
	}
	
	protected void addMine() {
		this.mine = true;
	}
	
	protected void setMinesAround(int nroMinesAround) {
		this.nroMinesAround = nroMinesAround;
	}

	@Override
	public boolean isMine() {
		return mine;
	}

	@Override
	public void marker() {
		this.marked = true;
	}

	@Override
	public void unmarker() {
		this.marked = false;
	}

	@Override
	public boolean isMarker() {
		return marked;
	}

	@Override
	public void changedVisited() {
		this.visited = true;
	}

	@Override
	public boolean isVisited() {
		return visited;
	}

	@Override
	public RowAndColumn getPosition() {
		return this.position;
	}

	@Override
	public int nroMinesAround() {
		return this.nroMinesAround;
	}
}
