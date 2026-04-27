package org.example.Pages;

import org.example.CommonUtils.commonUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DemoAppPage {
    WebDriver driver;
    commonUtil commonUtil;
    public DemoAppPage(WebDriver driver) {
        this.driver = driver;
        commonUtil = new commonUtil(driver);



    }

    public void brokenLinks(){

        List<WebElement> links = driver.findElements((By.xpath("//div[@id=\"broken-links\"]//a")));

        for (WebElement link: links){
            String getRef = link.getAttribute("href");
            try{
                URL url = new URL(getRef);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode()!=200){
                    System.out.println("Broken links"+httpURLConnection.getResponseMessage());
                }
                else{
                    System.out.println("Not a Broken links"+httpURLConnection.getResponseMessage());

                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public void handleMouseActions(){
        Actions action = new Actions(driver);
        action.moveToElement(commonUtil.getElement("//button[text()='Point Me']")).perform();

        List<WebElement> dropDownList = commonUtil.getElements("//div[@class='dropdown-content']/a");
        for (WebElement ele:dropDownList){
            System.out.println(ele.getText());
        }
    }

    public void handleAlerts(){
        commonUtil.click("//button[@id='alertBtn']");
        Alert alert = driver.switchTo().alert();
        alert.accept();

        for(int i=0;i<2;i++){
            commonUtil.click("//button[@id='confirmBtn']");
            Alert confirmAlt = driver.switchTo().alert();
            if(i==1){
                confirmAlt.accept();
            }
            else{
                confirmAlt.dismiss();
            }

        }

        for(int i=0;i<2;i++){
            commonUtil.click("//button[@id='promptBtn']");
            Alert confirmAlt = driver.switchTo().alert();
            if(i==1){
                confirmAlt.sendKeys("Shilajit");
                confirmAlt.accept();
            }
            else{
                confirmAlt.dismiss();
            }

        }

    }
    //promptBtn


}
