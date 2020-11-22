package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class Browser {
    private static WebDriver driver = null;
    private static BrowserFactory browserFactory = new BrowserFactory();

    public static WebDriver getDriver() {
        String browser = PropertyReader.getProperty("browser");

        if (driver == null) {
            driver = browserFactory.createBrowserSession(browser);
        }
        driver.manage().timeouts().implicitlyWait(
                Long.parseLong(PropertyReader.getProperty("default-wait")), SECONDS);
        driver.manage().timeouts().pageLoadTimeout(100, SECONDS);
        driver.manage().timeouts().setScriptTimeout(100,SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public static void stopDriver() {
        driver.quit();
        driver = null;
    }
}
