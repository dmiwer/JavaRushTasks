package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape;
    private GameObject platform;
    private int score;
    private boolean isGameStopped;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        showGrid(false);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        if (score > 0) score--;
        check();
        setScore(score);
        drawScene();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x >= WIDTH || x < 0 || y >= HEIGHT || y < 0) return;
        super.setCellColor(x, y, color);
    }

    @Override
    public void onKeyPress(Key key) {
        if (isGameStopped && key == Key.SPACE) {
            createGame();
            return;
        }

        switch (key) {
            case UP:
                isUpPressed = true;
                break;
            case LEFT:
                isLeftPressed = true;
                isRightPressed = false;
                break;
            case RIGHT:
                isRightPressed = true;
                isLeftPressed = false;
                break;
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key) {
            case UP:
                isUpPressed = false;
                break;
            case LEFT:
                isLeftPressed = false;
                break;
            case RIGHT:
                isRightPressed = false;
                break;
        }
    }

    private void createGame() {
        createGameObjects();
        drawScene();
        setTurnTimer(50);
        score = 1000;
        isGameStopped = false;
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellColor(x, y, Color.BLACK);
            }
        }
        landscape.draw(this);
        rocket.draw(this);
    }

    private void createGameObjects() {
        rocket = new Rocket(WIDTH / 2, 0);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
        platform = new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);
    }

    private void check() {
        if (rocket.isCollision(platform) && rocket.isStopped())
            win();
        else if (rocket.isCollision(landscape))
            gameOver();
    }

    private void win() {
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "Win!", Color.GREEN, 70);
        stopTurnTimer();
    }

    private void gameOver() {
        rocket.crash();
        score = 0;
        isGameStopped = true;
        showMessageDialog(Color.NONE, "it`s over", Color.RED, 70);
        stopTurnTimer();

    }

}
