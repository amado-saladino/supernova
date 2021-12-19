import helpers.Screenshot;
import org.testng.annotations.Test;
import pages.ColorPage;

public class TestWait extends TestCase {
    @Test
    void testColorWait() {
        ColorPage colorPage = new ColorPage(driver);
        String path = System.getProperty("user.dir") + "/static/colors.html";
        driver.navigate().to("file://" + path);
        colorPage.waitForColors();
        Screenshot.fullPageScreenshot("colors");
    }
}
