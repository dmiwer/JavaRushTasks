package com.javarush.games.racer;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.road.RoadManager;

public class RacerGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;
    private RoadMarking roadMarking;
    private PlayerCar player;
    private RoadManager roadManager;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            super.setCellColor(x, y, color);
        }
    }

    @Override
    public void onTurn(int step) {
        if (roadManager.checkCrush(player))
            gameOver();
        else {
            roadManager.generateNewRoadObjects(this);
            moveAll();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT:
                player.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                player.setDirection(Direction.RIGHT);
                break;
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key) {
            case LEFT:
                if (player.getDirection() == Direction.LEFT) player.setDirection(Direction.NONE);
                break;
            case RIGHT:
                if (player.getDirection() == Direction.RIGHT) player.setDirection(Direction.NONE);
                break;
        }
    }

    private void moveAll() {
        roadMarking.move(player.speed);
        roadManager.move(player.speed);
        player.move();
    }

    private void createGame() {
        isGameStopped = false;
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        setTurnTimer(40);
        roadManager = new RoadManager();
        drawScene();
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.NONE, "it`s over", Color.DEEPPINK, 70);
        stopTurnTimer();
        player.stop();
    }

    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        roadManager.draw(this);
        player.draw(this);
    }

    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x == CENTER_X)
                    setCellColor(x, y, Color.WHITE);
                else if (x >= ROADSIDE_WIDTH && x < WIDTH - ROADSIDE_WIDTH)
                    setCellColor(x, y, Color.DIMGREY);
                else
                    setCellColor(x, y, Color.GREEN);
            }
        }
    }
}
