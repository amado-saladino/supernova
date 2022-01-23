package pages;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ColorPage extends Page {
    public ColorPage(WebDriver driver) {
        super(driver);
    }

    
    public void waitForColors() {
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
    }
}
