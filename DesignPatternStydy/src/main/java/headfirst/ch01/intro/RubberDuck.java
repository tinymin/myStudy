package headfirst.ch01.intro;

public class RubberDuck extends Duck {
    @Override
    public void quack() {
        // Do Nothing
    }

    @Override
    public boolean display() {
        System.out.println("고무오리");
        return true;
    }
}
