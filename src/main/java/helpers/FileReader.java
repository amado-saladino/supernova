package helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    private String contents;
    public FileReader(String file) {
        try {
            this.contents = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            System.out.printf("The file '%s' does not exist%n", file);
            e.printStackTrace();
        }
    }

    public String toString() {
        return contents;
    }
}
