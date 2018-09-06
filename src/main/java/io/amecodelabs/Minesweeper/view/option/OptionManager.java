package io.amecodelabs.Minesweeper.view.option;

import java.util.Hashtable;
import java.util.Optional;

public class OptionManager {
	private Hashtable<String, Option> hashtable;
	
	public OptionManager() {
		this.hashtable = new Hashtable<>();
	}
	
	public void registerCommand(String nameCommand, Option command) {
		this.hashtable.put(nameCommand, command);
	}
	
	public void invoke(String nameCommand) {
		Optional<Option> optional = Optional.ofNullable(hashtable.get(nameCommand));
		optional.ifPresent((option) -> option.action());
	}
}
