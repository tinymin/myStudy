package headfirst.ch01.intro;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuckTest {
    @Test
    void testDuckDisplay() {
        Duck mallardDuck = new MallardDuck();
        Assert.assertTrue(mallardDuck.display());

        RedheadDuck redheadDuck = new RedheadDuck();
        Assert.assertTrue(redheadDuck.display());

        RubberDuck rubberDuck = new RubberDuck();
        Assert.assertTrue(rubberDuck.display());
    }
}