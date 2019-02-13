package from.wiki.strategy.practice.sample2;

import from.wiki.strategy.practice.sample2.animal.*;

public class StrategyMain {
    public static void main(String[] args) {
        Animal bear = new Bear();
        bear.performFly();
        bear.performCry();
        System.out.println("------------------");

        Animal bee = new Bee();
        bee.performFly();
        bee.performCry();
        System.out.println("------------------");

        Animal crow = new Crow();
        crow.performFly();
        crow.performCry();
        System.out.println("------------------");

        Animal salmon = new Salmon();
        salmon.performFly();
        salmon.performCry();
        System.out.println("------------------");
    }
}
