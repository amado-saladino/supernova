package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BrowserFactory {
    final static Map<String, Supplier<WebDriver>> map = new HashMap<>();
    static {
        map.put("CHROME", ()->new ChromeDriver(chromeOptions(false)));
        map.put("CHROME-HEADLESS", ()-> {
            return new ChromeDriver(chromeOptions(true));
        });
        map.put("FIREFOX",()->new FirefoxDriver(firefoxOptions(false)));
        map.put("FIREFOX-HEADLESS",()->new FirefoxDriver(firefoxOptions(true)));
    }

    public WebDriver createBrowserSession(String browser) {
        Supplier<WebDriver> driver = map.get(browser.toUpperCase());
        if (driver !=null) {
            return driver.get();
        }
        throw new IllegalArgumentException("No Such browser found: " + browser.toUpperCase());
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        System.setProperty("webdriver.chrome.driver", PropertyReader.getProperty("chromedriver-path"));
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default.content_settings.popups", 0);
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension",false);
        if (headless) {
            options.setHeadless(true);
            //options.addArguments("headless");
            options.addArguments("window-size=2000,1080");
        }
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        System.setProperty("webdriver.gecko.driver", PropertyReader.getProperty("geckodriver-path"));
        FirefoxOptions options = new FirefoxOptions();
        //option.addPreference("browser.download.folderList", 2);
        //option.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
        //option.addPreference("browser.download.manager.showWhenStarting", false);
        //option.addPreference("pdfjs.disabled", true);
        if (headless) {
            options.setHeadless(true);
        }
        return options;
    }
}
