package TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.example.CommonUtils.Extentsutil;
import org.example.CommonUtils.ScreenshotUtil;
import org.example.DriverFactory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.lang.reflect.Field;

public class Listeners extends ScreenshotUtil implements ITestListener {



    ExtentReports extentReports = Extentsutil.getReport();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public Listeners() {
    }


    @Override
    public void onTestStart(ITestResult result) {
       test.set(extentReports.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL,"Test FAILED");
        test.get().fail(result.getThrowable());

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            try {
                Field driverField = result.getInstance().getClass().getDeclaredField("driver");
                driverField.setAccessible(true);
                driver = (WebDriver) driverField.get(result.getInstance());
            } catch (Exception e) {
                test.get().warning("Unable to resolve WebDriver for screenshot: " + e.getMessage());
            }
        }

        if (driver == null) {
            test.get().warning("Screenshot skipped because WebDriver was null.");
            return;
        }

        try {
            String path = takeScreenshot(result.getMethod().getMethodName(), driver);
            test.get().addScreenCaptureFromPath(path, result.getMethod().getMethodName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS,"Test PASSED");

    }


    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extentReports.flush();

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }
}
