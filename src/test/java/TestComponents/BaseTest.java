package TestComponents;

import org.apache.commons.io.FileUtils;
import org.example.DriverFactory.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class BaseTest {

    private Properties prop;

    public BaseTest(){

        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/config.properties"
            );
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return prop.getProperty("url");
    }

    public String getBrowser() {
        return prop.getProperty("browser");
    }

    public String getGlobalTime() {
        return prop.getProperty("globalTimeout");
    }

    public String getExplicitTime() {
        return prop.getProperty("explicitTimeout");
    }

    public WebDriver setup(){
        DriverFactory.initDriver();
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        String url = getUrl();
        driver.get(url);
    return driver;
    }



    @AfterMethod
    public void tearDown(){
       DriverFactory.quitDriver();
    }
}
