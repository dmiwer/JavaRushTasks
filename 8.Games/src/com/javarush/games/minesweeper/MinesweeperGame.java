package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;

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
        countMineNeighbors();
    }

    private void countMineNeighbors() {
        for (GameObject[] gameObjects : gameField) {
            for (GameObject gameObject : gameObjects) {
                if (!gameObject.isMine){
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
