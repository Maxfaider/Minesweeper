package io.amecodelabs.Minesweeper.view;

import java.util.Scanner;

import io.amecodelabs.Minesweeper.config.language.SystemLanguage;
import io.amecodelabs.Minesweeper.view.option.OptionManager;

public class ConsoleView {
	private SystemLanguage systemLanguage;
	private OptionManager optionManager;
	private Scanner scanner;
	private String entryUser;
	 
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
	
	public void stageCustom() {
		System.out.println(systemLanguage.getText("expected-input"));
		System.out.print("\u261E");
		entryUser = this.scanner.nextLine();
		if(entryUser.matches("(\\d+)(\\s)(\\d+)(\\s)(\\d+)")) {
			String[] params = entryUser.split("\\s");
			stagePlay(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
		}
	}
	
	public void stagePlay(int rows, int columns, int mines) {
		//played
		System.out.println(">>" + systemLanguage.getText("title") + "<<");
		System.out.println(systemLanguage.getText("text-mines") + mines);
		System.out.println("Matriz");
		System.out.println(systemLanguage.getText("text-move"));
		System.out.print("\u261E");
		entryUser = this.scanner.nextLine();
	}
	
	public void selectedOption() {
		if(entryUser.matches("(\\d+)"))
			this.optionManager.invoke(entryUser);
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

}
