package helpers;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JsonReader {
    /**
     * Iterate over Json data source from Json file
     * @param file Json file to read
     * @param keyName Key name inside Json file
     * @return Iterator over list of maps
     */
    public static Iterator<Map> dataIterator(String file, String keyName) {
        List<Map> data = new ArrayList<>();
        JSONArray elements=null;
        try {
            String jsonFile = new String(Files.readAllBytes(Paths.get(file)));
            elements = new JSONObject(jsonFile).getJSONArray(keyName);

            for (Object element:elements) {
                data.add( ((JSONObject) element).toMap());
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return data.iterator();
    }

    /**
     * Data iterator for json file
     * @param file json file path
     * @param expression GPath inside the json file
     * @param i something to differentiate from the other method overloading
     * @return iterator of Map of json objects
     */
    public static Iterator<Map> dataIterator(String file, String expression, int i) {
        List<Map> data = null;
        try {
            String jsonFile = new String(Files.readAllBytes(Paths.get(file)));
            data = JsonPath.read(jsonFile, expression);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.iterator();
    }
}
