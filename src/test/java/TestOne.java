import org.testng.annotations.Test;
import pages.PageOne;

import java.io.IOException;

public class TestOne extends TestCase {
    @Test
    void testOne_one() {
        System.out.printf("driver is null? %s%n", driver == null);
        PageOne pg1 = new PageOne(driver);
        pg1.getElement();
        pg1.getTopMenu();
    }

    /**
     * review nginx-deploy folder
     */
    @Test
    void testOne_two() {
        PageOne pg1 = new PageOne(driver);
        driver.get("https://2886795347-80-frugo04.environments.katacoda.com/");
        pg1.waitForColors();
    }

    @Test
    void testOne_three() {
        PageOne pg1 = new PageOne(driver);
        pg1.findSearchByjQuery();
    }
}
