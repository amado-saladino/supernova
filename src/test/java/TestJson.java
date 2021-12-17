import helpers.JsonReader;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;

public class TestJson {
    @Test
    void testScreenshot() {
        System.out.println("This method test will create a screenshot on failure");
        Assert.assertEquals(1, 2);
    }

    @Test(dataProvider = "jsonDetails")
    void testThree_two(Map user) {
        String name = (String) user.get("name");
        String email = (String) user.get("email");
        String password = (String) user.get("password");
        String city =  (String) ((Map) user.get("address")).get("city");
        String country =  (String) ((Map) user.get("address")).get("country");

        System.out.printf("Name: %s%n", name);
        System.out.printf("Email: %s%n", email);
        System.out.printf("Password: %s%n", password);
        System.out.printf("City: %s%n", city);
        System.out.printf("Country: %s%n", country);
        System.out.println("---");
    }

    @Test(dataProvider = "jsonPath")
    void testJsonPath(Map user) {
        String name = (String) user.get("name");
        String email = (String) user.get("email");
        String password = (String) user.get("password");
        String city =  (String) ((Map) user.get("address")).get("city");
        String country =  (String) ((Map) user.get("address")).get("country");

        System.out.printf("Name: %s%n", name);
        System.out.printf("Email: %s%n", email);
        System.out.printf("Password: %s%n", password);
        System.out.printf("City: %s%n", city);
        System.out.printf("Country: %s%n", country);
        System.out.println("---");
    }

    @DataProvider(name = "jsonDetails")
    Iterator<Map> readJsonDetails(ITestNGMethod testNGMethod) {
        return JsonReader.dataIterator("data/data.json", "users");
    }

    @DataProvider(name = "jsonPath")
    Iterator<Map> readJsonpath() {
        return JsonReader.dataIterator("data/data.json", "$.users", 1);
    }
}
