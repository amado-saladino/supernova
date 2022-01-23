package helpers;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public abstract class Browser {
    private static WebDriver driver = null;
    private static BrowserFactory browserFactory = new BrowserFactory();

    public static WebDriver createDriver() {
        if (driver == null) {
            driver = browserFactory.createBrowserSession();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static WebDriver getCurrentDriver() {
        return driver;
    }

    public static void stopDriver() {
        driver.quit();
        driver = null;
    }
}
