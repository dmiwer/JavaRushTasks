package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.List;

public class Hippodrome {
    private List<Horse> horses;
    static Hippodrome game;

    public List<Horse> getHorses() {
        return horses;
    }

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public static void main(String[] args) {
        game = new Hippodrome(new ArrayList<>());

        game.getHorses().add(new Horse("one", 3, 0));
        game.getHorses().add(new Horse("two", 3, 0));
        game.getHorses().add(new Horse("six", 3, 0));
    }
}
