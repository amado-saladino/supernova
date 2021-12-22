package logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import helpers.Browser;
import helpers.ExtentManager;
import helpers.Screenshot;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Listener implements ITestListener {
    Map<String,ExtentTest> testClasses;
    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    @Override
    public void onTestStart(ITestResult result) {
        String testClassName = result.getTestClass().getName();
        String testMethodName = result.getMethod().getMethodName();
        ExtentTest test;

        if (testClasses.containsKey(testClassName)) {
            test =testClasses.get(testClassName).createNode(testMethodName);
        } else {
            ExtentTest t = extent.createTest(testClassName);
            test = t.createNode(testMethodName);
            testClasses.put(testClassName,t);
        }
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String msg = "<b>" + result.getMethod().getMethodName() + " passed</b>";
        Markup markup = MarkupHelper.createLabel(msg, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS,markup);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String exception = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.get().fail("<details><summary><b><font color=red>" +
                "Exception occurred: Click for details:</font></b></summary>" +
                exception.replaceAll(",","<br>") + "</details> \n");
        try {
            //condition could be made here, if the package name
            //contains 'rest' or 'backend', it will throw 'NullPointerException'
            if (result.getTestClass().getName().contains("TestRest")) {
                System.out.println("Class Name: " + result.getTestClass().getName());
                throw new NullPointerException();
            }

        String screenshotName = result.getTestClass().getName() +
                result.getMethod().getMethodName();

            String screenshotPah = Screenshot.fullPageScreenshot(screenshotName);

            extentTest.get().fail("<b><font color=red>Failure screenshot",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPah).build());
        } catch (NullPointerException e) {
            extentTest.get().fail("This test might be a non-GUI test case<br>" +
                    "No screenshot could be taken");
        } catch (Exception e) {
            extentTest.get().fail("Cannot attach the screenshot");
        }
        String msg = "<b>" + result.getMethod().getMethodName() + " failed</b>";
        Markup markup = MarkupHelper.createLabel(msg, ExtentColor.RED);
        extentTest.get().log(Status.FAIL,markup);

        //allure report attach screenshot
        WebDriver driver = Browser.getCurrentDriver();
        if (driver instanceof WebDriver) {
            saveFailureScreenShot(driver);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String msg = "<b>" + result.getMethod().getMethodName() + " skipped</b>";
        Markup markup = MarkupHelper.createLabel(msg, ExtentColor.GREY);
        extentTest.get().log(Status.SKIP,markup);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
        testClasses = new HashMap<>();
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent !=null) {
            extent.flush();
        }
    }
    @Attachment
    public byte[] saveFailureScreenShot(WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
