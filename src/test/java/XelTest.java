import org.testng.annotations.Test;

import java.util.Map;

public class XelTest extends TestCase {
    @Test
    void testTwo_one() {
        System.out.printf("driver is null? %s", driver == null);
    }

    @Test(dataProvider = "xlDataSource")
    void testTwo_two(String name, String phone, String email) {
        System.out.printf("Name: %s%n", name);
        System.out.printf("Phone: %s%n", phone);
        System.out.printf("Email: %s%n", email);
        System.out.println("---");
    }

    @Test(dataProvider = "xlMapIteratorSource")
    void testTwo_three(Map user) {
        String name = (String) user.get("Name");
        String phone = (String) user.get("Phone");
        String email = (String) user.get("Email");

        System.out.printf("Name: %s%n", name);
        System.out.printf("Phone: %s%n", phone);
        System.out.printf("Email: %s%n",email);
        System.out.println("---");
    }

    @Test(dataProvider = "xlDataIteratorSource")
    void testTwo_four(String name, String phone, String email) {
        System.out.printf("Name: %s%n", name);
        System.out.printf("Phone: %s%n", phone);
        System.out.printf("Email: %s%n", email);
        System.out.println("---");
    }

    @Test(dataProvider = "xlDataMapSource")
    void testTwo_five(Map user) {
        String name = (String) user.get("Name");
        String phone = (String) user.get("Phone");
        String email = (String) user.get("Email");

        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("---");
    }
}
