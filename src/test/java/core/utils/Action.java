package core.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class Action {

    protected WebDriver driver = Core.getDriver();
    private final int DURATION = 15;
    private Actions action;

    protected void println(String value) {
        DateFormat df = new SimpleDateFormat("YY-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        String date = df.format(new Date());
        String ANSI_ORANGE = "\u001B[33m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_ORANGE + "[" + date + "] -> " + ANSI_GREEN + value + ANSI_RESET);
    }

    protected WebElement element(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION));
        return wait.until(d -> driver.findElement(locator));
    }

    protected WebElement element(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(d -> driver.findElement(locator));
    }

    protected List<WebElement> elements(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION));
        return wait.until(e -> driver.findElements(locator));
    }

    protected List<WebElement> elements(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(d -> driver.findElements(locator));
    }

    protected Boolean element(ExpectedCondition<Boolean> condition) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION));
        return wait.until(condition);
    }

    protected WebElement element(ExpectedCondition<WebElement> condition, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(condition);
    }

    protected void clickAction(By locator) {
        this.println("[Click] -> " + locator);
        try {
            action = new Actions(driver);
            action.click(element(locator)).build().perform();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void click(By locator) {
        this.println("[Click] -> " + locator);
        try {
            element(locator).click();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void sendKeysAction(By locator, String value) {
        this.println("[SendKeys] -> " + locator + " => " + value);
        try {
            action = new Actions(driver);
            action.sendKeys(element(locator), value).build().perform();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void sendKeysAction(By locator, Keys value) {
        this.println("[SendKeys] -> " + locator + " => " + value);
        try {
            action = new Actions(driver);
            action.sendKeys(element(locator), value).build().perform();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void sendKeys(By locator, String value) {
        this.println("[SendKeys] -> " + locator + " => " + value);
        try {
            element(locator).sendKeys(value);
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void sendKeys(By locator, Keys value) {
        this.println("[SendKeys] -> " + locator + " => " + value);
        try {
            element(locator).sendKeys(value);
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void sendKeysPress(By locator, String value) {
        try {
            this.println("[SendKeys] -> " + locator + " => " + value);
            Random r = new Random();
            for (int i = 0; i < value.length(); i++) {
                try {
                    Thread.sleep((int) (r.nextGaussian() * 12 + 110));
                } catch (InterruptedException ignored) {
                }
                String s = new StringBuilder().append(value.charAt(i)).toString();
                element(locator).sendKeys(s);
            }
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void selectText(By locator, String text) {
        try {
            this.println("[SelectByVisibleText] -> " + locator + " => " + text);
            Select typeSelect = new Select(element(locator));
            typeSelect.selectByVisibleText(text);
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void selectValue(By locator, String text) {
        try {
            this.println("[selectByValue] -> " + locator + " => " + text);
            Select typeSelect = new Select(element(locator));
            typeSelect.selectByValue(text);
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void selectTextReact(By locator, String text) {
        try {
            this.println("[SelectByTextReact] -> " + locator + " => " + text);
            click(locator);
            sleep(1);

            WebElement option = element(By.xpath("//*[contains(text(),'" + text + "')]"));
            this.println("[SelectByTextReact][Option]: " + option.getText());
            option.click();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(we.getMessage()));
        }
    }

    protected void filter(By contentlocators, String value) {
        this.println("[Filter] -> " + contentlocators + " => " + value);
        elements(contentlocators).stream()
                .filter(element -> element.getText().trim().equalsIgnoreCase(value))
                .findFirst()
                .ifPresent((e) -> {
                    try {
                        // WebElement::click
                        e.click();
                    } catch (Exception ex) {
                        script("arguments[0].click();", e);
                    }
                });
    }

    protected void clear(By locator) {
        try {
            this.println("[Clear] -> " + locator);
            element(locator).clear();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected String getValue(By locator) {
        try {
            this.println("[GetValue] -> " + locator);
            return element(locator).getDomProperty("value");
        } catch (RuntimeException we) {
            //ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected String getText(By locator) {
        try {
            this.println("[GetText] -> " + locator);
            return element(locator).getText();
        } catch (RuntimeException we) {
            //ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected String getDomProperty(By locator, String attribute) {
        try {
            this.println("[GetDomProperty] -> " + locator);
            return element(locator).getDomProperty(attribute);
        } catch (RuntimeException we) {
            //ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected Boolean isDisplayed(By locator) {
        this.println("[isDisplayed] -> " + locator);
        try {
            boolean is = element(locator).isDisplayed();
            return is;
        } catch (RuntimeException we) {
            return false;
        }
    }

    protected Boolean isDisplayed(By locator, int seg) {
        this.println("[isDisplayed] -> " + locator);
        try {
            boolean is = element(locator, seg).isDisplayed();
            return is;
        } catch (RuntimeException we) {
            return false;
        }
    }

    protected void sleep(Double seg) {
        this.println("[Sleep] -> " + seg + " seg");
        try {
            if (seg <= 0)
                return;
            Thread.sleep((long) (seg * 1000));
        } catch (InterruptedException we) {
            Thread.currentThread().interrupt();
        }
    }

    protected void sleep(int seg) {
        this.println("[Sleep] -> " + seg + " seg");
        try {
            if (seg <= 0)
                return;
            Thread.sleep(seg * 1000);
        } catch (InterruptedException we) {
            Thread.currentThread().interrupt();
        }
    }

    protected void scroll(int x, int y) {
        try {
            this.println("[Scroll] -> X: " + x + " => Y: " + y);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(" + x + "," + y + ")", "");
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected void scrollToElement(By locator) {
        this.println("[scrollToElement] -> " + locator);
        try {
            action = new Actions(driver);
            action.scrollToElement(element(locator)).build().perform();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void scrollElement(By locator) {
        try {
            this.println("[ScrollIntoView] -> " + locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", element(locator));
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected boolean isElementPresent(By locator, Boolean status) {
        this.println("[isElementPresent] -> " + locator + " => " + status);
        if (status) { // status true
            while (status)
                status = isDisplayed(locator, 1);
        } else if (!status) {
            while (!status)
                status = isDisplayed(locator, 1);
        }
        this.println("[isElementPresent] -> " + locator + " => " + status);
        return status;
    }

    protected void switchFrame(By locator) {
        try {
            this.println("[SwitchFrame] -> " + locator);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(element(locator));
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void switchFrame(int windows) {
        try {
            this.println("[SwitchFrame] -> " + windows);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(windows);
        } catch (RuntimeException we) {
            ErrorInteraction("Frame-" + String.valueOf(windows));
        }
    }

    protected void switchDefaultContent() {
        try {
            this.println("[SwitchDefaultContent]");
            driver.switchTo().defaultContent();
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected void switchToNewTab() {
        try {
            this.println("[SwitchToNewTab]");
            String currentWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(currentWindow)) {
                    currentWindow = windowHandle;
                    break;
                }
            }
            driver.close();
            driver.switchTo().window(currentWindow);
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected void switchToNewTab(int index) {
        try {
            this.println("[SwitchToNewTab] -> " + index);
            int i = 0;
            for (String windowHandle : driver.getWindowHandles()) {
                if (i == index) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
                i++;
            }
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected void switchToNewTab(String title) {
        try {
            this.println("[SwitchToNewTab] -> " + title);
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
                if (driver.getTitle().equals(title)) {
                    break;
                }
            }
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected void zoom(int size) {
        try {
            this.println("[Zoom] -> size" + size);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.style.zoom = '" + size + "%'");
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected void script(String script) {
        this.println("[Script] -> " + script);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script);
    }

    protected void script(String script, By locator) {
        this.println("[Script] -> " + script);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, locator);
    }

    protected void script(String script, WebElement element) {
        this.println("[Script] -> " + script);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, element);
    }

    private void ErrorInteraction(String description) {
        this.println("Error Interaction: " + description);
        throw new RuntimeException("Error Interaction: " + description);
    }
}
