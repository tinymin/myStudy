package headfirst.ch01.intro;

public abstract class Duck {
    public void quack() {
        System.out.println("Quack");
    }

    public void swim() {
        System.out.println("Can swimming.");
    }

    public void fly() {
        System.out.println("Can fly.");
    }

    public abstract void display();
}
