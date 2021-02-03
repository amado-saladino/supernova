package helpers;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Screenshot {
    static WebDriver driver = Browser.getCurrentDriver();
    private static Random random = new Random();

    public static String takeScreenshot(String imageFile) {
        String fingerprint = createDateTimeStamp();
        Path path = Paths.get("screenshots",imageFile+ fingerprint +".png");
        try {
            Files.createDirectories((path.getParent()));
            FileOutputStream fileOutputStream = new FileOutputStream(path.toString());
            fileOutputStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES) ) ;
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile+ fingerprint +".png";
    }

    private static String createDateTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }


}
