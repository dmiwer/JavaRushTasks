package com.javarush.games.racer.road;

import com.javarush.engine.cell.Game;
import com.javarush.games.racer.PlayerCar;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RoadManager {
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER;
    private static final int PLAYER_CAR_DISTANCE = 12;
    private static final int FIRST_LANE_POSITION = 16;
    private static final int FOURTH_LANE_POSITION = 44;
    private int passedCarsCount = 0;
    private List<RoadObject> items = new ArrayList<>();

    public int getPassedCarsCount() {
        return passedCarsCount;
    }

    private boolean isRoadSpaceFree(RoadObject roadObject) {
        for (RoadObject item : items) {
            if (item.isCollisionWithDistance(roadObject, PLAYER_CAR_DISTANCE))
                return false;
        }
        return true;
    }

    private void addRoadObject(RoadObjectType type, Game game) {
        int x = game.getRandomNumber(FIRST_LANE_POSITION, FOURTH_LANE_POSITION);
        int y = -1 * RoadObject.getHeight(type);
        RoadObject roadObject = createRoadObject(type, x, y);
        if (roadObject != null && isRoadSpaceFree(roadObject)) items.add(roadObject);
    }

    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        switch (type) {
            case THORN:
                return new Thorn(x, y);
            case DRUNK_CAR:
                return new MovingCar(x, y);
            default:
                return new Car(type, x, y);
        }
    }

    private void generateRegularCar(Game game) {
        int carTypeNumber = game.getRandomNumber(4);
        if (game.getRandomNumber(100) < 30)
            addRoadObject(RoadObjectType.values()[carTypeNumber], game);
    }

    private void deletePassedItems() {
        items.removeIf(roadObject -> {
            if (roadObject.y < RacerGame.HEIGHT) return false;
            if (roadObject.type != RoadObjectType.THORN) passedCarsCount++;
            return true;
        });
    }

    public void draw(Game game) {
        for (RoadObject item : items) {
            item.draw(game);
        }
    }

    public void move(int boost) {
        for (RoadObject item : items) {
            item.move(boost + item.speed, items);
        }
        deletePassedItems();
    }

    private boolean isThornExists() {
        for (RoadObject item : items)
            if (item.type == RoadObjectType.THORN) return true;
        return false;
    }

    private boolean isMovingCarExists() {
        for (RoadObject item : items)
            if (item.type == RoadObjectType.DRUNK_CAR) return true;
        return false;
    }

    private void generateThorn(Game game) {
        if (game.getRandomNumber(100) < 10 && !isThornExists())
            addRoadObject(RoadObjectType.THORN, game);
    }

    private void generateMovingCar(Game game) {
        if (game.getRandomNumber(100) < 10 && !isMovingCarExists())
            addRoadObject(RoadObjectType.DRUNK_CAR, game);
    }

    public void generateNewRoadObjects(Game game) {
        generateThorn(game);
        generateMovingCar(game);
        generateRegularCar(game);
    }

    public boolean checkCrush(PlayerCar playerCar) {
        for (RoadObject item : items) {
            if (item.isCollision(playerCar)) return true;
        }
        return false;
    }
}
