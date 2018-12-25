package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private int countFlags;


    private void openTile(int x, int y) {
        gameField[y][x].isOpen = true;
        setCellColor(x, y, Color.GREEN);
        if (gameField[y][x].isMine)
            setCellValue(x, y, MINE);
        else if (gameField[y][x].countMineNeighbors != 0)
            setCellNumber(x, y, gameField[y][x].countMineNeighbors);
        else {
            for (GameObject neighbor : getNeighbors(gameField[y][x]))
                if (!neighbor.isOpen) openTile(neighbor.x, neighbor.y);
            setCellValue(x, y, "");
        }
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        super.onMouseLeftClick(x, y);
        openTile(x, y);
    }

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private void createGame() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                setCellColor(x, y, Color.ORANGE);
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
