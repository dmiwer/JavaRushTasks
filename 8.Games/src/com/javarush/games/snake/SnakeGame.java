package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
            setScore(score += 5);
            setTurnTimer(turnDelay -= 10);
        }
        if (!snake.isAlive) gameOver();
        if (snake.getLength() > GOAL) win();
            drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case SPACE:
                if (isGameStopped) createGame();
                break;
        }
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple() {
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        } while (snake.checkCollision(apple));
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "it`s over", Color.DEEPPINK, 50);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "YOU WIN", Color.DEEPPINK, 50);
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        score = 0;
        setScore(score);
        turnDelay = 300;
        setTurnTimer(turnDelay);
        createNewApple();
        isGameStopped = false;
        drawScene();
    }
}
