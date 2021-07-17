package helpers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonReader {
    /**
     * Iterate over Json data source from Json file
     * @param file Json file to read
     * @param keyName Key name inside Json file
     * @return Iterator over array of objects
     */
    public static Iterator<Object> dataIterator(String file, String keyName) {
        JSONArray elements=null;
        List<Object> data = new ArrayList<>();
        try {
            String fileData = new String(Files.readAllBytes(Paths.get(file)));
            elements = new JSONObject(fileData).getJSONArray(keyName);

            for (Object element:elements) {
                data.add( ((JSONObject) element).toMap());
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return data.iterator();
    }
}
