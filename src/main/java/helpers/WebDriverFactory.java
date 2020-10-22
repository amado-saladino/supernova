package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class WebDriverFactory {
    private static WebDriver driver = null;

    public static WebDriver getDriver() {
        String browser = PropertyReader.getProperty("browser");
        setDriverPath();

        if (driver == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver(chromeOption());
                    break;
                case "firefox":
                    driver = new FirefoxDriver(firefoxOption());
                    break;
                case "chrome-headless":
                    ChromeOptions options = chromeOption();
                    options.setHeadless(true);
                    //options.addArguments("headless");
                    options.addArguments("window-size=2000,1080");
                    driver = new ChromeDriver(options);
                    break;
                case "firefox-headless":
                    FirefoxOptions firefoxOptions = firefoxOption();
                    firefoxOptions.setHeadless(true);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                default:
                    System.out.println("Invalid browser option");
                    System.exit(1);
            }
        }
        driver.manage().timeouts().implicitlyWait(
                Long.parseLong(PropertyReader.getProperty("default-wait")), SECONDS);
        driver.manage().timeouts().pageLoadTimeout(100, SECONDS);
        driver.manage().timeouts().setScriptTimeout(100,SECONDS);
        return driver;
    }

    private static void setDriverPath() {
        System.setProperty("webdriver.chrome.driver", PropertyReader.getProperty("chromedriver-path"));
    }

    public static void stopDriver() {
        driver.quit();
        driver = null;
    }

    private static ChromeOptions chromeOption() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default.content_settings.popups", 0);
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension",false);
        return options;
    }

    private static FirefoxOptions firefoxOption() {
        FirefoxOptions option = new FirefoxOptions();
        //option.addPreference("browser.download.folderList", 2);
        //option.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
        //option.addPreference("browser.download.manager.showWhenStarting", false);
        //option.addPreference("pdfjs.disabled", true);
        return option;
    }
}
