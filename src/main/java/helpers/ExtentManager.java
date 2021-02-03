package helpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;
    public static ExtentReports createInstance() {
        ExtentSparkReporter spark = new ExtentSparkReporter("screenshots/report.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Test Automation Reports");
        spark.config().setReportName("Test Results");

        extent = new ExtentReports();
        extent.setSystemInfo("Browser","Chrome - headless");
        extent.attachReporter(spark);
        return extent;
    }
}
