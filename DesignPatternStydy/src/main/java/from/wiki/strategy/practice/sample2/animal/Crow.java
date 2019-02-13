package from.wiki.strategy.practice.sample2.animal;

import from.wiki.strategy.practice.sample2.cry.CryImpl;
import from.wiki.strategy.practice.sample2.fly.Flyable;

/***
 * 까마귀
 */
public class Crow extends Animal {
    public Crow() {
        this.me = "까마귀 : ";
        this.fly = new Flyable();
        this.cry = new CryImpl("끄아아악 끄아아악");
    }

    @Override
    public String foods() {
        return "벌레";
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
