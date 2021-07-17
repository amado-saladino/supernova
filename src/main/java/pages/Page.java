package pages;

import helpers.FileReader;
import helpers.WaitUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public abstract class Page {
    protected WebDriver driver;
    protected JavascriptExecutor jsRunner;
    WaitUtils wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        jsRunner = (JavascriptExecutor) driver;
        wait = new WaitUtils();
    }

    public void selectDropdownItem(WebElement comboElement, String option) {
        new Select(comboElement).selectByVisibleText(option);
    }

    public void selectDropdownItem(WebElement comboElement, int index) {
        new Select(comboElement).selectByIndex(index);
    }

    public WebElement getSelectedDropdownItem(WebElement comboBox) {
        return new Select(comboBox).getFirstSelectedOption();
    }

    protected <T> T runScript(String script,Object... args) {
        return (T) jsRunner.executeScript(script, args);
    }

    void scrollToBottom() {
        runScript("scrollTo(0, document.body.scrollHeight)");
    }

    public void injectScriptFile(String file) {
        String script = new FileReader("scripts\\" + file).toString();
        runScript(script);
    }
}
