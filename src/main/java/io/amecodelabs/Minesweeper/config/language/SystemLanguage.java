package io.amecodelabs.Minesweeper.config.language;

import java.util.Locale;
import java.util.ResourceBundle;

public class SystemLanguage {
	private ResourceBundle resorceBundle;
	
	public static SystemLanguage newInstance() {
		return new SystemLanguage(
				ResourceBundle.getBundle("language", Locale.getDefault())
		);
	}
	
	private SystemLanguage(ResourceBundle resourceBundle) {
		this.resorceBundle = resourceBundle;
	}
	
	public String getText(String key) {
		return this.resorceBundle.getString(key);
	}
}
