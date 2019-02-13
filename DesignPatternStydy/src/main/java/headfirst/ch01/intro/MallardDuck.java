package headfirst.ch01.intro;

public class MallardDuck extends Duck {
    @Override
    public boolean display() {
        try {
            System.out.println("청둥오리");
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }
}
