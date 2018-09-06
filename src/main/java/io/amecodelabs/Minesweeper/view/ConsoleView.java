package io.amecodelabs.Minesweeper.view;

import java.util.Scanner;

import io.amecodelabs.Minesweeper.config.language.SystemLanguage;
import io.amecodelabs.Minesweeper.game.Board;
import io.amecodelabs.Minesweeper.game.BoardBuilder;
import io.amecodelabs.Minesweeper.game.Cell;
import io.amecodelabs.Minesweeper.game.MinesWeeperException;
import io.amecodelabs.Minesweeper.view.option.OptionManager;

public class ConsoleView {
	private SystemLanguage systemLanguage;
	private OptionManager optionManager;
	private Scanner scanner;
	private Board board;
	private String entryUser;
	private String[] params;
	 
	public ConsoleView(SystemLanguage systemLanguage) {
		this.systemLanguage = systemLanguage;
		this.optionManager = new OptionManager();
		scanner = new Scanner(System.in);
		configConsoleView();
	}
	
	private void configConsoleView() {
		this.optionManager.registerCommand("1", this::onOptionBeginner);
		this.optionManager.registerCommand("2", this::onOptionIntermediate);
		this.optionManager.registerCommand("3", this::onOptionExpert);
		this.optionManager.registerCommand("4", this::onOptionCustom);
		this.optionManager.registerCommand("5", () -> System.exit(0));
		this.optionManager.registerCommand("U", this::onUncover);
		this.optionManager.registerCommand("M", this::onMark);
		this.optionManager.registerCommand("D", this::onUnmark);
	}
	
	public void run() {
		stageMain();
	}
	
	public void stageMain() {
		do {
			System.out.println(">>" + systemLanguage.getText("title") + "<<");
			System.out.println(systemLanguage.getText("level"));
			System.out.println(systemLanguage.getText("option-beginner"));
			System.out.println(systemLanguage.getText("option-intermediate"));
			System.out.println(systemLanguage.getText("option-expert"));
			System.out.println(systemLanguage.getText("option-custom"));
			System.out.println(systemLanguage.getText("option-exit"));
			System.out.print("\u261E");
			entryUser = this.scanner.nextLine();
			selectedOption();
		} while(true);
	}
	
	public void selectedOption() {
		if(entryUser.matches("(\\d+)"))
			this.optionManager.invoke(entryUser);
	}
	
	public void stageCustom() {
		System.out.println(systemLanguage.getText("expected-input"));
		System.out.print("\u261E");
		entryUser = this.scanner.nextLine();
		//restrict zero values
		if(entryUser.matches("([1-9][0-9]*|0*[1-9][0-9]*)\\s([1-9][0-9]*|0*[1-9][0-9]*)\\s([1-9][0-9]*|0*[1-9][0-9]*)")) {
			String[] params = entryUser.split("\\s");
			stagePlay(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
		} else {
			System.out.println(systemLanguage.getText("input-wrong"));
		}
	}
	
	private boolean record_stagePlay;
	 
	public void stagePlay(int rows, int columns, int mines) {
		record_stagePlay = true;
		System.out.println(record_stagePlay);
		createGame(rows, columns, mines);
		while(record_stagePlay) {
			System.out.println(">>" + systemLanguage.getText("title") + "<<");
			System.out.println(systemLanguage.getText("text-mines") + mines + "     " + "[0] Exit");
			showBoard(true);
			System.out.println(systemLanguage.getText("text-move"));
			System.out.print("\u261E");
			entryUser = this.scanner.nextLine();
			if(entryUser.matches("(\\d+)(\\s)(\\d+)(\\s)[U|M|D]")) {
				params = entryUser.split("\\s");
				optionManager.invoke(params[2]);
			} else if (entryUser.equals("0")) {
				board = null;
				return;
			} else 
				System.out.println(systemLanguage.getText("input-wrong"));
		}
	}
		
	private void createGame(int rows, int columns, int mines) {
		BoardBuilder boardBuilder = BoardBuilder.createGame(rows, columns, mines);
		boardBuilder
				.setWinnerListener(this::onWinner)
				.setLoserListener(this::onLoser)
				.setHandleInvalidCellError(this::onInvalidCellError)
				.setHandleMarkerCellError(this::onMarkerCellError)
				.setHandleVisitedCellError(this::onVisitedCellError)
				.setObserver(this::onUpdateBoard);
		try {
			board = boardBuilder.build();
		} catch (MinesWeeperException e) {
			//
		}
	}
	
	private void showBoard(boolean hiddenMines) {
		Cell[][] cells = board.getCells();
		//header matrix
		System.out.print(" \t");
		for(int i=1; i <= cells[0].length; i++) {
			if(i>0 && i<10) 
				System.out.print("0"+ i + " ");
			else
				System.out.print(i + " ");
		}
		System.out.println("\n");
		
		for (int i = 0; i < cells.length; i++) {
			System.out.print(i+1 + "\t");
			for (int j = 0; j < cells[i].length; j++) {
				if(cells[i][j].isMine() && !hiddenMines)
					System.out.print("*  ");
				else if(cells[i][j].isMarker())
					System.out.print("P  ");
				else if(cells[i][j].isVisited())
					if(cells[i][j].nroMinesAround() == 0) {
						System.out.print("_  ");
					} else {
						System.out.print(cells[i][j].nroMinesAround() + "  ");
					}
				else
					System.out.print(".  ");
			}
			System.out.println();
		}
	}
	
	private void onOptionExpert() {
		stagePlay(16, 30, 99);
	}
	
	private void onOptionIntermediate() {
		stagePlay(16, 16, 40);
	}
	
	private void onOptionBeginner() {
		stagePlay(9, 9, 10);
	}
	
	private void onOptionCustom() {
		stageCustom();
	}
	
	private void onWinner(Board board) {
		record_stagePlay = false;
		System.out.println(systemLanguage.getText("text-win"));
		showBoard(false);
		terminatePlay();
	}
	
	private void onLoser(Board board) {
		record_stagePlay = false;
		System.out.println(systemLanguage.getText("text-loser"));
		showBoard(false);
		terminatePlay();
	}
	
	private void terminatePlay() {
		System.out.println(systemLanguage.getText("text-terminated"));
		this.scanner.nextLine();
	}
	
	private void onInvalidCellError(MinesWeeperException ex) {
		System.out.println(ex.getMessage());
	}
	
	private void onMarkerCellError(MinesWeeperException ex) {
		System.out.println(ex.getMessage());
	}
	
	private void onVisitedCellError(MinesWeeperException ex) {
		System.out.println(ex.getMessage());
	}
	
	private void onUpdateBoard(Board board, Cell cell) {
		//jobless
	}
	
	private void onUncover() {
		int row = Integer.parseInt(params[0])-1;
		int column = Integer.parseInt(params[1])-1;
		board.play(row, column);
	}
	
	private void onMark() {
		int row = Integer.parseInt(params[0])-1;
		int column = Integer.parseInt(params[1])-1;
		board.markCell(row, column);
	}
	
	private void onUnmark() {
		int row = Integer.parseInt(params[0])-1;
		int column = Integer.parseInt(params[1])-1;
		board.unmarkCell(row, column);
	}
}
