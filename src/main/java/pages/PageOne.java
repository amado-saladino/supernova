package pages;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PageOne extends Page {
    public PageOne(WebDriver driver) {
        super(driver);
        injectScriptFile("jquery.js");
    }

    public void getElement() {
        WebElement regLink = driver.findElement(By.cssSelector("a.ico-register"));
        String t = runScript("return arguments[0].text", regLink);
        System.out.printf("Text of the link: %s%n", t);
    }

    public void getTopMenu() {
        WebElement top = driver.findElement(By.cssSelector("ul.top-menu"));
        List<WebElement> items = runScript("return arguments[0].querySelectorAll('li')", top);
        items.forEach(item -> System.out.println(item.getText()));
    }

    /**
     * https://www.techbeamers.com/webdriver-fluent-wait-command-examples/
     */
    public void waitForColors() {
        /*Function<WebDriver,Boolean> function = new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver arg0) {
                WebElement element = arg0.findElement(By.id("dynamicColor"));
                String color = element.getCssValue("color");
                System.out.println("The button text has color :" + color);
                if (color.equals("rgba(255, 255, 0, 1)")) {
                    return true;
                }
                return false;
            }
        };*/
        wait.waitForCustomCondition(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                WebElement element = d.findElement(By.id("dynamicColor"));
                String color = element.getCssValue("color");
                System.out.println("The button text has color :" + color);
                if (color.equals("rgba(255, 255, 0, 1)")) {
                    return true;
                }
                return false;
            }
        }, 30);
        //boolean elementFound= wait.waitForCustomCondition(function, 30);
        //System.out.println(elementFound);
    }

    public void findSearchByjQuery() {
        String srch = runScript("return $('#small-searchterms').attr('placeholder')");
        System.out.printf("Placeholder: %s%n", srch);
    }
}
