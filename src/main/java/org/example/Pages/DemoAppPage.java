package org.example.Pages;

import org.example.CommonUtils.commonUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DemoAppPage {
    WebDriver driver;
    commonUtil commonUtil;

    private String listOfBrokenLinksTag = "//div[@id='broken-links']//a";
    public DemoAppPage(WebDriver driver) {
        this.driver = driver;
        commonUtil = new commonUtil(driver);

    }

    public void brokenLinks(){

        List<WebElement> links = commonUtil.getElements(listOfBrokenLinksTag);

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

    public String fillBasicFormDetails(String name, String email, String phone, String address) {
        commonUtil.sendKeys("#name", name);
        commonUtil.sendKeys("#email", email);
        commonUtil.sendKeys("#phone", phone);
        commonUtil.sendKeys("#textarea", address);

        return commonUtil.getElement("#name").getAttribute("value") + "|"
                + commonUtil.getElement("#email").getAttribute("value") + "|"
                + commonUtil.getElement("#phone").getAttribute("value") + "|"
                + commonUtil.getElement("#textarea").getAttribute("value");
    }

    public boolean selectGender(String gender) {
        String genderLocator = "#" + gender.toLowerCase();
        commonUtil.click(genderLocator);
        return commonUtil.getElement(genderLocator).isSelected();
    }

    public List<String> selectDays(List<String> days) {
        List<String> selectedDays = new ArrayList<>();

        for (String day : days) {
            String dayLocator = "#" + day.toLowerCase();
            WebElement dayCheckbox = commonUtil.getElement(dayLocator);

            if (!dayCheckbox.isSelected()) {
                dayCheckbox.click();
            }

            if (dayCheckbox.isSelected()) {
                selectedDays.add(day.toLowerCase());
            }
        }

        return selectedDays;
    }

    public String selectCountry(String countryName) {
        WebElement countryDropdown = commonUtil.getElement("#country");
        Select country = new Select(countryDropdown);
        country.selectByVisibleText(countryName);
        return country.getFirstSelectedOption().getText().trim();
    }

    public List<String> selectColors(List<String> colors) {
        WebElement colorsDropdown = commonUtil.getElement("#colors");
        Select colorSelect = new Select(colorsDropdown);
        colorSelect.deselectAll();

        for (String color : colors) {
            colorSelect.selectByVisibleText(color);
        }

        return colorSelect.getAllSelectedOptions()
                .stream()
                .map(option -> option.getText().trim())
                .collect(Collectors.toList());
    }

    public String selectDateRange(String startDate, String endDate) {
        setDateValue("#start-date", startDate);
        setDateValue("#end-date", endDate);
        commonUtil.click(".submit-btn");
        return commonUtil.waitForVisibleText("#result");
    }

    public String uploadSingleFile(String filePath) {
        commonUtil.getElement("#singleFileInput").sendKeys(filePath);
        commonUtil.click("#singleFileForm button[type='submit']");
        return commonUtil.waitForVisibleText("#singleFileStatus");
    }

    public String getStaticTablePrice(String bookName) {
        String priceLocator = "//table[@name='BookTable']//tr[td[normalize-space()='" + bookName + "']]/td[4]";
        return commonUtil.getText(priceLocator).trim();
    }

    public boolean selectProductFromPagination(String pageNumber, String productName) {
        commonUtil.waitForNumberOfElements("#productTable tbody tr", 0);
        commonUtil.click("//ul[@id='pagination']//a[normalize-space()='" + pageNumber + "']");
        commonUtil.waitForText("#productTable tbody", productName);

        WebElement productCheckbox = commonUtil.getElement(
                "//table[@id='productTable']//tr[td[normalize-space()='" + productName + "']]//input[@type='checkbox']"
        );
        productCheckbox.click();
        return productCheckbox.isSelected();
    }

    public String toggleDynamicButton() {
        WebElement startButton = commonUtil.getElement("button[name='start']");
        startButton.click();
        WebElement stopButton = commonUtil.waitForVisibleElement("button[name='stop']");
        return stopButton.getText().trim();
    }

    public String copyTextUsingDoubleClick() {
        WebElement copyButton = commonUtil.getElement("//button[normalize-space()='Copy Text']");
        new Actions(driver).doubleClick(copyButton).perform();
        return commonUtil.getElement("#field2").getAttribute("value");
    }

    private void setDateValue(String locator, String value) {
        WebElement dateElement = commonUtil.getElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];", dateElement, value);
    }

}
