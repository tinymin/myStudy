package headfirst.ch01.intro;

public class DecoyDuck extends Duck {
    @Override
    public void quack() {
        // Do Nothing
    }

    @Override
    public void fly() {
        // Do Nothing
    }

    @Override
    public void display() {
        System.out.println("가짜오리");
    }
}
