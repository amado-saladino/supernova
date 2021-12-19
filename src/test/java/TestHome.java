import helpers.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class TestHome extends TestCase {
    @Test
    void testJscript() {
        System.out.printf("driver is null? %s%n", driver == null);
        HomePage homePage = new HomePage(driver);
        homePage.getElement();
        homePage.getTopMenu();
    }

    @Test
    void testJQuery() {
        HomePage homePage = new HomePage(driver);
        homePage.findSearchByjQuery();
    }

    @Test
    void testFakeGenerator() {
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
