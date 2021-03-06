package helpers;

import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class Browser {
    private static WebDriver driver = null;
    private static BrowserFactory browserFactory = new BrowserFactory();

    public static WebDriver createDriver(String browserType, boolean headless) {
        if (driver == null) {
            driver = browserFactory.createBrowserSession(browserType, headless);

            driver.manage().timeouts().implicitlyWait(
                    Long.parseLong(PropertyReader.getProperty("default-wait")), SECONDS);
            driver.manage().timeouts().pageLoadTimeout(100, SECONDS);
            driver.manage().timeouts().setScriptTimeout(100, SECONDS);
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
