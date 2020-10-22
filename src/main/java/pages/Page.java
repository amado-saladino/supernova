package pages;

import helpers.FileReader;
import helpers.WaitUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
