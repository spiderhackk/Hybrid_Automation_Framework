package Tests;

import TestComponents.BaseTest;
import org.apache.commons.io.FileUtils;
import org.example.Pages.DemoAppPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class DemoTest extends BaseTest {
    WebDriver driver;
    DemoAppPage demoAppPage ;

    @BeforeMethod
    public void launchApp(){
        this.driver=setup();
        demoAppPage =new DemoAppPage(driver);
    }

    @Test()
    public void brokenLinksTest(){

        demoAppPage.brokenLinks();

//        Assert.assertEquals("Demo","Dem");
        }

    @Test()
    public void handleMouseAction() {

        demoAppPage.handleMouseActions();
    }

    @Test()
    public void handleAlerts(){
        demoAppPage.handleAlerts();
    }


}


