package io.amecodelabs.Minesweeper;

import io.amecodelabs.Minesweeper.config.language.SystemLanguage;
import io.amecodelabs.Minesweeper.view.ConsoleView;

public class App 
{
	
    public static void main(String[] args)
    {
    	new ConsoleView(SystemLanguage.newInstance()).run();
    }
}
