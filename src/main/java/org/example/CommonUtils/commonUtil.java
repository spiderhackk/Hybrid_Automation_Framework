package org.example.CommonUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class commonUtil {
    private final WebDriver driver;

    public commonUtil(WebDriver driver) {
        this.driver = driver;
    }

    public JavascriptExecutor js() {
        return (JavascriptExecutor) driver;
    }

    public By checkLocatorType(String ele) {
        String locator = ele.trim();

        if (isXpath(locator)) {
            return By.xpath(locator);
        }
        if (isCss(locator)) {
            return By.cssSelector(locator);
        }

        return findUsingFallbacks(locator);
    }

    public WebElement getElement(String locator) {
        WebElement element = driver.findElement(checkLocatorType(locator));
        scrollToElement(element);
        return element;
    }

    public List<WebElement> getElements(String locator) {
        return driver.findElements(checkLocatorType(locator));
    }

    public void scrollToElement(WebElement element) {
        js().executeScript(
                "arguments[0].scrollIntoView({behavior: 'instant', block: 'center', inline: 'nearest'});",
                element
        );
    }

    public void scrollToElement(String locator) {
        scrollToElement(driver.findElement(checkLocatorType(locator)));
    }

    public void click(String locator) {
        getElement(locator).click();
    }

    public void sendKeys(String locator, String value) {
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    public String getText(String locator) {
        return getElement(locator).getText();
    }

    private boolean isXpath(String locator) {
        return locator.startsWith("/") || locator.startsWith("./") || locator.startsWith("(//");
    }

    private boolean isCss(String locator) {
        return locator.startsWith("#")
                || locator.startsWith(".")
                || locator.contains("[")
                || locator.contains(">")
                || locator.contains(":");
    }

    private By findUsingFallbacks(String locator) {
        By[] fallbacks = new By[] {
                By.id(locator),
                By.name(locator),
                By.className(locator),
                By.tagName(locator),
                By.linkText(locator),
                By.partialLinkText(locator)
        };

        for (By by : fallbacks) {
            try {
                driver.findElement(by);
                return by;
            } catch (NoSuchElementException ignored) {
            }
        }

        throw new NoSuchElementException("Unable to identify locator type for: " + locator);
    }
}
