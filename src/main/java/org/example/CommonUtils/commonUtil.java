package org.example.CommonUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class commonUtil {
    private final WebDriver driver;
    private final AutoHealLocator autoHealLocator;
    private final WebDriverWait wait;

    public commonUtil(WebDriver driver) {
        this.driver = driver;
        this.autoHealLocator = new AutoHealLocator(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        WebElement element = findElement(locator, checkLocatorType(locator));
        scrollToElement(element);
        return element;
    }

    public List<WebElement> getElements(String locator) {
        By by = checkLocatorType(locator);
        List<WebElement> elements = driver.findElements(by);
        if (!elements.isEmpty()) {
            autoHealLocator.capture(locator, by, elements.get(0));
            return elements;
        }

        return List.of(autoHealLocator.findHealedElement(locator, by));
    }

    public void scrollToElement(WebElement element) {
        js().executeScript(
                "arguments[0].scrollIntoView({behavior: 'instant', block: 'center', inline: 'nearest'});",
                element
        );
    }

    public void scrollToElement(String locator) {
        scrollToElement(getElement(locator));
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

    public WebElement waitForVisibleElement(String locator) {
        return wait.until(driver -> {
            try {
                WebElement element = getElement(locator);
                return element.isDisplayed() ? element : null;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return null;
            }
        });
    }

    public String waitForVisibleText(String locator) {
        return waitForVisibleElement(locator).getText();
    }

    public void waitForText(String locator, String expectedText) {
        wait.until(driver -> {
            try {
                return getElement(locator).getText().contains(expectedText);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return false;
            }
        });
    }

    public void waitForNumberOfElements(String locator, int minCount) {
        wait.until(driver -> getElements(locator).size() > minCount);
    }

    private WebElement findElement(String locator, By by) {
        try {
            WebElement element = driver.findElement(by);
            autoHealLocator.capture(locator, by, element);
            return element;
        } catch (NoSuchElementException e) {
            return autoHealLocator.findHealedElement(locator, by);
        }
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
