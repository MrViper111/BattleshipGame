package dev.mrviper111.game.enums;

public enum Difficulty {

    EASY {
        @Override
        public int getBoardSize() {
            return 8;
        }
    },
    MEDIUM {
        @Override
        public int getBoardSize() {
            return 10;
        }
    },
    HARD {
        @Override
        public int getBoardSize() {
            return 15;
        }
    };

    public int getBoardSize() {
        return 0;
    }

}
