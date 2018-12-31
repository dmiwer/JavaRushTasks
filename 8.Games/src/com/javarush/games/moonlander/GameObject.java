package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class GameObject {
    public double x;
    public double y;
    public int width;
    public int height;
    public int[][] matrix;

    public GameObject(double x, double y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }

    public void draw(Game game){
        if (matrix == null) return;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                game.setCellColor(x + (int)this.x, y + (int)this.y, Color.values()[matrix[y][x]]);
            }
        }
    }
}
