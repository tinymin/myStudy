package from.wiki.strategy.practice.sample2.animal;

import from.wiki.strategy.practice.sample2.cry.CryImpl;
import from.wiki.strategy.practice.sample2.fly.NoFly;

/***
 * 곰
 */
public class Bear extends Animal {
    public Bear() {
        this.me = "곰 : ";
        this.fly = new NoFly();
        this.cry = new CryImpl("크아아아아아아아");
    }

    @Override
    public String foods() {
        return "너";
    }

    @Override
    public String swim() {
        return me + "수영한다";
    }

    @Override
    public String run() {
        return me + "뛴다";
    }
}
