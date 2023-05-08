package dev.mrviper111;

import dev.mrviper111.game.Difficulty;
import dev.mrviper111.game.Game;

public class BattleshipGame {

    public static void main(String[] args) {
        Game game = new Game(Difficulty.EASY, "MrViper111");
        game.init();
    }

}
