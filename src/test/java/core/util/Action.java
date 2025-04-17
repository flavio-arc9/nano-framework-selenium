package core.util;

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
    private final int DURATION = 10;
    private Actions action;

    private void println(String value) {
        DateFormat df = new SimpleDateFormat("YY-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        String date = df.format(new Date());
        System.out.println("[" + date + "] " + value);
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

    protected String getValue(By locator) {
        try {
            this.println("[GetValue] -> " + locator);
            return element(locator).getDomProperty("value");
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected void clear(By locator) {
        try {
            this.println("[Clear] -> " + locator);
            element(locator).clear();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected String getText(By locator) {
        try {
            this.println("[GetText] -> " + locator);
            return element(locator).getText();
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected String getDomProperty(By locator, String attribute) {
        try {
            this.println("[GetDomProperty] -> " + locator);
            return element(locator).getDomProperty(attribute);
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected Boolean isDisplayed(By locator) {
        this.println("[isDisplayed] -> " + locator);
        try {
            boolean is = element(locator).isDisplayed();
            this.println("[isDisplayed] -> " + locator + " => " + is);
            return is;
        } catch (RuntimeException we) {
            this.println("[isDisplayed] -> " + locator + " => " + false);
            return false;
        }
    }

    protected Boolean isDisplayed(By locator, int seg) {
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
            if (seg <= 0) return;
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

    private void ErrorInteraction(String description) {
        this.println("Error Interaction : " + description);
        driver.close();
    }
}
