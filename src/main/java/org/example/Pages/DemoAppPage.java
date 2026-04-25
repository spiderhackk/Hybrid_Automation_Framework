package org.example.Pages;

import org.example.CommonUtil.Utils;
import org.openqa.selenium.WebDriver;

public class DemoAppPage extends Utils {


    public DemoAppPage(WebDriver driver) {
        super(driver);

    }

    public void handle() {
        click("//button[text()='START']");
        type("//input[@id=\"name\"]","shilajit");

    }
}
