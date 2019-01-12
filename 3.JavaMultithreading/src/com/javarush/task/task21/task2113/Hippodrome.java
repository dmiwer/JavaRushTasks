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

    void run() {
        for (int i = 0; i < 100; i++) {
            move();
            print();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void move() {
        for (Horse horse : horses) {
            horse.move();
        }
    }

    void print() {
        for (Horse horse : horses) {
            horse.print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public Horse getWinner() {
        Horse winner = null;
        for (Horse horse : horses) {
            if (winner == null || winner.getDistance() < horse.getDistance())
                winner = horse;
        }
        return winner;
    }

    void printWinner() {
        System.out.printf("Winner is %s!%n", getWinner().getName());
    }

    public static void main(String[] args) {
        game = new Hippodrome(new ArrayList<>());

        game.getHorses().add(new Horse("one", 3, 0));
        game.getHorses().add(new Horse("two", 3, 0));
        game.getHorses().add(new Horse("six", 3, 0));

        game.run();
    }
}
