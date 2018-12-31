package com.javarush.games.game2048;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

public class Game2048 extends Game {
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
//        gameField = new int[][]{{2,4,8,16},{32,64,128,256},{512,1024,2048,0},{2,8,4,16}};
        drawScene();
    }

    private void createNewNumber() {
        int x, y;
        do {
            x = getRandomNumber(SIDE);
            y = getRandomNumber(SIDE);
        } while (gameField[y][x] != 0);
        if (getRandomNumber(10) == 9)
            gameField[y][x] = 4;
        else
            gameField[y][x] = 2;
    }

    private boolean compressRow(int[] row) {
        boolean res = false;
        for (int i = 0; i < SIDE - 1; i++) {
            for (int j = i + 1; j < SIDE; j++) {
                if (row[i] == 0 && row[j] != 0) {
                    int t = row[i];
                    row[i] = row[j];
                    row[j] = t;
                    res = true;
                }
            }
        }
        return res;
    }

    private boolean mergeRow(int[] row) {
        boolean res = false;
        for (int i = 0; i < SIDE - 1; i++) {
            if (row[i] != 0 && row[i] == row[i + 1]) {
                row[i] *= 2;
                row[i + 1] = 0;
                res = true;
            }
        }
        return res;
    }

    private void rotateClockwise() {
        int[][] rotatedField = new int[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++)
            for (int j = 0; j < SIDE; j++)
                rotatedField[i][j] = gameField[SIDE - j - 1][i];
        gameField = rotatedField;
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT:
                moveLeft();
                drawScene();
                break;
            case RIGHT:
                moveRight();
                drawScene();
                break;
            case UP:
                moveUp();
                drawScene();
                break;
            case DOWN:
                moveDown();
                drawScene();
                break;
        }
    }

    private void moveLeft() {
        boolean res = false;
        for (int[] ints : gameField)
            if (compressRow(ints) | mergeRow(ints)) {
                compressRow(ints);
                res = true;
            }
        if (res) createNewNumber();

    }

    private void moveRight() {
    }

    private void moveUp() {
    }

    private void moveDown() {
    }

    private Color getColorByValue(int value) {
        switch (value) {
            case 0:
                return Color.WHITE;
            case 2:
                return Color.AQUA;
            case 4:
                return Color.DEEPPINK;
            case 8:
                return Color.GREEN;
            case 16:
                return Color.ORANGE;
            case 32:
                return Color.RED;
            case 64:
                return Color.YELLOW;
            case 128:
                return Color.WHEAT;
            case 256:
                return Color.ANTIQUEWHITE;
            case 512:
                return Color.AQUAMARINE;
            case 1024:
                return Color.CADETBLUE;
            case 2048:
                return Color.VIOLET;
        }
        return Color.BLACK;
    }

    private void setCellColoredNumber(int x, int y, int value) {
        String val = value == 0 ? "" : String.valueOf(value);
        setCellValueEx(x, y, getColorByValue(value), val);
    }

    private void drawScene() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                setCellColoredNumber(x, y, gameField[y][x]);
            }
        }
    }

    private void createGame() {
        createNewNumber();
        createNewNumber();
    }
}
