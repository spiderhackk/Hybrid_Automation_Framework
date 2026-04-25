package TestComponents;

import org.example.DriverFactory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    WebDriver driver;

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
        driver = DriverFactory.getDriver();
        String url = getUrl();
        driver.get(url);
    return driver;
    }

    @AfterTest
    public void tearDown(){
       DriverFactory.quitDriver();
    }
}
