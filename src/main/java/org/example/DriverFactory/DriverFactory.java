package org.example.DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(){

        driver.set(new ChromeDriver());
    }


    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {

        driver.get().quit();
    }
}
