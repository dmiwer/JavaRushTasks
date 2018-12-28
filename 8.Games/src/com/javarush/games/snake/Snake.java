package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();
    private Direction direction = Direction.LEFT;
    public boolean isAlive = true;

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(){

    }

    public void draw(Game game) {
        Color color = isAlive ? Color.BLACK : Color.RED;
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject gObj = snakeParts.get(i);
            if (i == 0)
                game.setCellValueEx(gObj.x, gObj.y, Color.NONE, HEAD_SIGN, color, 75);
            else
                game.setCellValueEx(gObj.x, gObj.y, Color.NONE, BODY_SIGN, color, 75);
        }
    }
}
