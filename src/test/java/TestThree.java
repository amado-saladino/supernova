import org.testng.annotations.Test;
import pages.PageOne;

import java.util.Map;

public class TestThree extends TestCase {
    @Test
    void testThree_one() {
        System.out.printf("driver is null? %s%n", driver == null);
        PageOne pg1 = new PageOne(driver);
        pg1.getElement();
        pg1.getTopMenu();
    }

    @Test(dataProvider = "jsonDetails")
    void testThree_two(Map user) {
        String name = (String) user.get("name");
        String email = (String) user.get("email");
        String password = (String) user.get("password");

        System.out.printf("Name: %s%n", name);
        System.out.printf("Email: %s%n", email);
        System.out.printf("Password: %s%n", password);
        System.out.println("---");
    }
}
