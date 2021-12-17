package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YAMLReader {
    private static final Yaml yaml = new Yaml();

    public static Iterator<Map> yamlIterator(String file) {
        List<Map> containers = null;
        File yamlFile = new File(file);
        try {
            InputStream inputStream = new FileInputStream(yamlFile);
            Map pod = yaml.load(inputStream);
            Map spec = (Map) pod.get("spec");

            containers = (List<Map>) spec.get("containers");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return containers.iterator();
    }

    public static Iterator<Map> getJsonFromYAML(String file, String expression) {
        final File yamlFile = new File(file);
        final Yaml yml = new Yaml();
        List<Map> data = null;

        try {
            final Object loadedYaml = yml.load(new FileReader(yamlFile));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(loadedYaml, LinkedHashMap.class);
            data = JsonPath.read(json, expression);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data.iterator();
    }
}
