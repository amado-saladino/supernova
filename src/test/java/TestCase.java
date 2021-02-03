import helpers.Browser;
import helpers.ExcelReader;
import helpers.JsonReader;
import helpers.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class TestCase {
    protected static WebDriver driver;

    @BeforeSuite
    void setupTest() {
        driver = Browser.createDriver(BrowserType.CHROME, true);
        //driver.manage().window().fullscreen();
        driver.navigate().to(PropertyReader.getProperty("BASE_URL"));
    }

    @DataProvider(name = "xlDataSource")
    Object[][] readData(ITestNGMethod testNGMethod) {
        return ExcelReader.loadTabularData("data\\" + testNGMethod.getRealClass().getSimpleName() + ".xlsx",
                testNGMethod.getMethodName());
    }

    @DataProvider(name = "xlMapIteratorSource")
    Iterator<Object> readMapIterator(ITestNGMethod testNGMethod) {
        return ExcelReader.dataMapIterator("data\\" + testNGMethod.getRealClass().getSimpleName() + ".xlsx",
                testNGMethod.getMethodName());
    }

    @DataProvider(name = "xlDataIteratorSource")
    Iterator<Object[]> readDataIterator(ITestNGMethod testNGMethod) {
        return ExcelReader.dataIterator("data\\" + testNGMethod.getRealClass().getSimpleName() + ".xlsx",
                testNGMethod.getMethodName());
    }

    @DataProvider(name = "xlDataMapSource")
    Object[] readMappedData(ITestNGMethod testNGMethod) {
        return ExcelReader.getMappedSheet("data\\" + testNGMethod.getRealClass().getSimpleName() + ".xlsx",
                testNGMethod.getMethodName());
    }

    @DataProvider(name = "jsonDetails")
    Iterator<Object> readJsonDetails(ITestNGMethod testNGMethod) {
        return JsonReader.dataIterator("data/" + testNGMethod.getRealClass().getSimpleName()  +".json",testNGMethod.getMethodName());
    }

    @Test
    void test1() {
        System.out.println("test1");
    }

    @AfterSuite
    void teardownTest() {
        Browser.stopDriver();
    }
}
