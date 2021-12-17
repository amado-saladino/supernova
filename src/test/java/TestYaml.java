import helpers.YAMLReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;

public class TestYaml {
    @Test(dataProvider = "yaml")
    void testYaml(Map container) {
        String name = (String) container.get("name");
        String image = (String) container.get("image");

        System.out.printf("Name: %s%n", name);
        System.out.printf("Email: %s%n", image);
    }

    @Test(dataProvider = "containers")
    void testContainers(Map container) {
        String name = (String) container.get("name");
        String image = (String) container.get("image");

        System.out.printf("Name: %s%n", name);
        System.out.printf("Email: %s%n", image);
    }

    @DataProvider(name = "yaml")
    Iterator<Map> readYAML() {
        return YAMLReader.yamlIterator("data/pod.yml");
    }

    @DataProvider(name = "containers")
    Iterator<Map> getContainers() {
        return YAMLReader.getJsonFromYAML("data/pod.yml", "$.spec.containers");
    }
}
