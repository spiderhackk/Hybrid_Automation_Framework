package org.example.CommonUtil;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class Utils implements WebActions {

    WebDriver driver;
    Wait<WebDriver> driverWait;
    Actions actions;
    public Utils(WebDriver driver){

        this.driver = driver;
    }

    public void takeScreenshot() {
        try{
            String timestamp = new SimpleDateFormat("dd-MM-yyy-HH:mm").format(new Date());
            File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            File des = new File(System.getProperty("user.dir")+"/screenshots/"+timestamp+".png");
            FileUtils.copyFile(src,des);
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }



    }
    public void scrollIntoView(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("argument[0].scrollIntoView(true);",element);

    }


    public WebElement waitUtilElementDisplay(String element){
        driverWait= new WebDriverWait(driver, Duration.ofSeconds(10));
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
    }


    @Override
    public void click(String element) {
        driver.findElement(By.xpath(element)).click();
    }

    @Override
    public void type(String ele, String type) {
        actions = new Actions(driver);

       WebElement element = waitUtilElementDisplay(ele);
//               driver.findElement(By.xpath(ele));
        actions.keyDown(element,Keys.SHIFT)
                .sendKeys(type.substring(0,1)).
                keyUp(Keys.SHIFT).sendKeys(type.substring(1)).perform();
    }
}
