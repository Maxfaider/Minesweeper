package io.amecodelabs.Minesweeper.game.events;

import java.util.function.Consumer;

import io.amecodelabs.Minesweeper.game.Board;

public interface WinnerListener extends Consumer<Board> {

}
