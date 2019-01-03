package com.javarush.games.racer;

import com.javarush.engine.cell.*;

public class GameObject {
    public int x;
    public int y;
    public int[][] matrix;
    public int width;
    public int height;

    public GameObject(int x, int y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Game game) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                game.setCellColor(this.x + x, this.y + y, Color.values()[matrix[y][x]]);
            }
        }
    }
}
