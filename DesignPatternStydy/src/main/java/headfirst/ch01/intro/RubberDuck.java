package headfirst.ch01.intro;

public class RubberDuck extends Duck {
    @Override
    public void quack() {
        System.out.println("삑삑");
    }

    @Override
    public void fly() {
        // Do Nothing
    }

    @Override
    public void display() {
        System.out.println("고무오리");
    }
}
