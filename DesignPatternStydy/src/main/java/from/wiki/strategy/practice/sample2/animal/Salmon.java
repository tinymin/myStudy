package from.wiki.strategy.practice.sample2.animal;

import from.wiki.strategy.practice.sample2.cry.NoCry;
import from.wiki.strategy.practice.sample2.fly.NoFly;

/***
 * 연어
 */
public class Salmon extends Animal {
    public Salmon() {
        this.me = "연어 : ";
        this.fly = new NoFly();
        this.cry = new NoCry();
    }

    @Override
    public String foods() {
        return "플랑크톤";
    }

    @Override
    public String swim() {
        return "수영함";
    }

    @Override
    public String run() {
        return null;
    }
}
