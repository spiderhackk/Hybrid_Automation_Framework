package org.example.CommonUtils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public ScreenshotUtil(){
    }

    public  String  takeScreenshot(String testCaseName, WebDriver driver)
            throws IOException {
            String timestamp = new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date());
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotDir = new File(System.getProperty("user.dir") + "/reports/screenshots");
            FileUtils.forceMkdir(screenshotDir);
            File des = new File(screenshotDir, timestamp + "_" + testCaseName + ".jpg");
            FileUtils.copyFile(src, des);
            return "screenshots/" + timestamp + "_" + testCaseName + ".jpg";


    }
}
