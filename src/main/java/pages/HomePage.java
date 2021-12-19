package pages;

import helpers.Screenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends Page {
    @FindBy(css="a.ico-register")
    private WebElement linkReg;

    @FindBy(css = "ul.top-menu")
    private WebElement menuTop;

    public HomePage(WebDriver driver) {
        super(driver);
        injectScriptFile("scripts/jquery.js");
    }

    public void getElement() {
        String t = runScript("return arguments[0].text", linkReg);
        System.out.printf("Text of the link: %s%n", t);
    }

    public void getTopMenu() {
        List<WebElement> items = runScript("return arguments[0].querySelectorAll('li')", menuTop);
        items.forEach(item -> System.out.println(item.getText()));
        Screenshot.elementScreenshot(menuTop, "menu");
    }

    public void findSearchByjQuery() {
        String srch = runScript("return $('#small-searchterms').attr('placeholder')");
        System.out.printf("Placeholder: %s%n", srch);
    }
}
