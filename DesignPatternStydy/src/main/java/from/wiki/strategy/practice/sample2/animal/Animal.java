package from.wiki.strategy.practice.sample2.animal;

import lombok.NoArgsConstructor;
import from.wiki.strategy.practice.sample2.cry.Cry;
import from.wiki.strategy.practice.sample2.fly.Fly;

@NoArgsConstructor
public abstract class Animal {
    protected String me;

    protected Fly fly;
    protected Cry cry;

    public void performFly() {
        System.out.println(me + fly.fly());
    }

    public void performCry() {
        System.out.println(me + cry.cry());
    }

    abstract String swim();
    abstract String foods();
    abstract String run();
}
