package com.javarush.games.racer.road;

import com.javarush.engine.cell.Game;
import com.javarush.games.racer.PlayerCar;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoadManager {
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER;
    private static final int FIRST_LANE_POSITION = 16;
    private static final int FOURTH_LANE_POSITION = 44;
    private List<RoadObject> items = new ArrayList<>();

    private void addRoadObject(RoadObjectType type, Game game) {
        int x = game.getRandomNumber(FIRST_LANE_POSITION, FOURTH_LANE_POSITION);
        int y = -1 * RoadObject.getHeight(type);
        RoadObject roadObject = createRoadObject(type, x, y);
        if (roadObject != null) items.add(roadObject);
    }

    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        switch (type) {
            case THORN:
                return new Thorn(x, y);
            default:
                return null;
        }
    }

    private void deletePassedItems() {
        items.removeIf(roadObject -> roadObject.y >= RacerGame.HEIGHT);
    }

    public void draw(Game game) {
        for (RoadObject item : items) {
            item.draw(game);
        }
    }

    public void move(int boost) {
        for (RoadObject item : items) {
            item.move(boost + item.speed);
        }
        deletePassedItems();
    }

    private boolean isThornExists() {
        for (RoadObject item : items)
            if (item.type == RoadObjectType.THORN) return true;
        return false;
    }

    private void generateThorn(Game game) {
        if (game.getRandomNumber(100) < 10 && !isThornExists())
            addRoadObject(RoadObjectType.THORN, game);
    }

    public void generateNewRoadObjects(Game game) {
        generateThorn(game);
    }

    public boolean checkCrush(PlayerCar playerCar) {
        for (RoadObject item : items) {
            if (item.isCollision(playerCar)) return true;
        }
        return false;
    }
}
