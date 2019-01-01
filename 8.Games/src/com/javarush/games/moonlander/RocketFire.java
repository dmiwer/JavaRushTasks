package com.javarush.games.moonlander;

import java.util.List;

public class RocketFire extends GameObject {
    private List<int[][]> frames;
    private int frameIndex;
    private boolean isVisible;

    public RocketFire(List<int[][]> frameList) {
        super(0, 0, frameList.get(0));
        frames = frameList;
        frameIndex = 0;
        isVisible = false;
    }
}
