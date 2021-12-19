package helpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Screenshot {
    private final static WebDriver driver = Browser.getCurrentDriver();

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

    public static String fullPageScreenshot(String imageName) {
        String fingerprint = createDateTimeStamp();
        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/" + imageName + fingerprint + ".png");
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageName + fingerprint + ".png";
    }

    public static void elementScreenshot(WebElement element, String imageName) {
        File source = element.getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/" + imageName + element.hashCode() + ".png");
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createDateTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }
}
