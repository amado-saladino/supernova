import helpers.Faker;
import helpers.Screenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PageOne;

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
        String path = System.getProperty("user.dir") + "/nginx-deploy/colors.html";
        //driver.get("https://2886795347-80-frugo04.environments.katacoda.com/");
        driver.get(path);
        pg1.waitForColors();
        Screenshot.takeScreenshot("colors");
    }

    @Test
    void testOne_three() {
        PageOne pg1 = new PageOne(driver);
        pg1.findSearchByjQuery();
    }

    @Test
    void testOne_four() {
        System.out.printf("driver is null? %s%n", driver == null);
        Faker faker = new Faker();
        String day = faker.getRandomDate().day();
        String month = faker.getRandomDate().month();
        String year = faker.getRandomDate().year();

        System.out.printf("Random day: %s%n",day);
        System.out.printf("Random month: %s%n",month);
        System.out.printf("Random year: %s%n",year);
    }
}
