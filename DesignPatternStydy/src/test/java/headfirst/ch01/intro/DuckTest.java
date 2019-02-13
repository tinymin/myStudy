package headfirst.ch01.intro;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

class DuckTest {
    @Test
    void testDuckDisplay() {
        Duck mallardDuck = new MallardDuck();
        assertNotNull(mallardDuck);
        System.out.println("-------------------");
        mallardDuck.display();
        mallardDuck.quack();
        mallardDuck.swim();
        mallardDuck.fly();

        Duck redheadDuck = new RedheadDuck();
        assertNotNull(redheadDuck);
        System.out.println("-------------------");
        redheadDuck.display();
        redheadDuck.quack();
        redheadDuck.swim();
        redheadDuck.fly();

        Duck rubberDuck = new RubberDuck();
        System.out.println("-------------------");
        assertNotNull(rubberDuck);
        rubberDuck.display();
        rubberDuck.quack();
        rubberDuck.swim();
        rubberDuck.fly();

        Duck decoyDuck = new DecoyDuck();
        System.out.println("-------------------");
        assertNotNull(decoyDuck);
        decoyDuck.display();
        decoyDuck.quack();
        decoyDuck.swim();
        decoyDuck.fly();
    }
}