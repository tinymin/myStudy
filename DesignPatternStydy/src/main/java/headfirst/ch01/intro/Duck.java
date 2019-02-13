package headfirst.ch01.intro;

public abstract class Duck {
    public void quack() {
        System.out.println("Quack");
    }

    public boolean swim() {
        return true;
    }

    public boolean fly() {
        return true;
    }

    public abstract boolean display();
}
