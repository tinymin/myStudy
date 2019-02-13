package from.wiki.strategy.practice.sample2.animal;

import from.wiki.strategy.practice.sample2.cry.NoCry;
import from.wiki.strategy.practice.sample2.fly.Flyable;

/***
 * 벌
 */
public class Bee extends Animal{
    public Bee() {
        this.me = "벌 : ";
        this.fly = new Flyable();
        this.cry = new NoCry();
    }

    @Override
    public String foods() {
        return "꿀";
    }

    @Override
    public String swim() {
        return null;
    }

    @Override
    public String run() {
        return null;
    }
}
