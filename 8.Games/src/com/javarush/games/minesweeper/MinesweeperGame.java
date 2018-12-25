package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countClosedTiles = SIDE * SIDE;
    private int countMinesOnField;
    private int countFlags;
    private int score;
    private boolean isGameStopped;


    private void openTile(int x, int y) {
        if (isGameStopped) return;
        if (gameField[y][x].isOpen) return;
        if (gameField[y][x].isFlag) return;
        setCellColor(x, y, Color.GREEN);
        gameField[y][x].isOpen = true;
        countClosedTiles--;
        if (gameField[y][x].isMine) {
            setCellValueEx(x, y, Color.RED, MINE);
            gameOver();
        } else {
            score += 5;
            setScore(score);
            if (countClosedTiles == countMinesOnField) {
                win();
            } else if (gameField[y][x].countMineNeighbors != 0) {
                setCellNumber(x, y, gameField[y][x].countMineNeighbors);
            } else {
                for (GameObject neighbor : getNeighbors(gameField[y][x]))
                    if (!neighbor.isOpen) openTile(neighbor.x, neighbor.y);
                setCellValue(x, y, "");
            }
        }
    }

    private void markTile(int x, int y) {
        if (isGameStopped) return;
        if (gameField[y][x].isOpen) return;
        if (gameField[y][x].isFlag) {
            gameField[y][x].isFlag = false;
            countFlags++;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.ORANGE);
            return;
        }
        if (countFlags == 0) return;
        gameField[y][x].isFlag = true;
        countFlags--;
        setCellValue(x, y, FLAG);
        setCellColor(x, y, Color.YELLOW);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        super.onMouseRightClick(x, y);
        markTile(x, y);
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        super.onMouseLeftClick(x, y);
        if (isGameStopped) restart();
        else openTile(x, y);
    }

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "it`s over", Color.DEEPPINK, 50);
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "WIN", Color.DEEPPINK, 50);
    }

    private void restart() {
        isGameStopped = false;
        countClosedTiles = SIDE * SIDE;
        countFlags = 0;
        score = 0;
        countMinesOnField = 0;
        setScore(score);
        createGame();
    }

    private void createGame() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                setCellColor(x, y, Color.ORANGE);
                setCellValue(x, y, "");
                boolean itIsMine = getRandomNumber(10) == 0;
                gameField[y][x] = new GameObject(x, y, itIsMine);
                if (itIsMine) countMinesOnField++;
            }
        }
        countFlags = countMinesOnField;
        countMineNeighbors();
    }

    private void countMineNeighbors() {
        for (GameObject[] gameObjects : gameField) {
            for (GameObject gameObject : gameObjects) {
                if (!gameObject.isMine) {
                    for (GameObject neighbor : getNeighbors(gameObject)) {
                        if (neighbor.isMine) gameObject.countMineNeighbors++;
                    }
                }
            }
        }
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                if (dx == 0 && dy == 0) continue;
                if (gameObject.x + dx < 0 || gameObject.x + dx == SIDE) continue;
                if (gameObject.y + dy < 0 || gameObject.y + dy == SIDE) continue;
                result.add(gameField[gameObject.y + dy][gameObject.x + dx]);
            }
        }
        return result;
    }
}
