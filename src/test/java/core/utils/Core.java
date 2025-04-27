package core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Core {

    private static WebDriver browserDriver = null;

    private Properties props() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/test/java/config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error al cargar el archivo config.properties");
        }
        return properties;
    }

    private void validateProps(String field, String value) {
        if (value.equals("")) {
            String msg = String.format("No se ha configurado el campo %s en el archivo config.properties", field);
            throw new Error(msg);
        }
    }

    @Before
    public void start(Scenario scenario) {
        String environment = System.getProperty("cucumber.env");
        Properties properties = props();
        String baseUrl = properties.getProperty(environment + ".baseurl", "");
        String platform = properties.getProperty(environment + ".platform", "chrome");
        String headless = properties.getProperty(environment + ".headless", "false");

        validateProps("baseurl", baseUrl);
        validateProps("platform", platform);

        List<String> arguments = new ArrayList<>(List.of(
                "--verbose",
                "--remote-allow-origins=*",
                "--disable-web-security",
                "--ignore-certificate-errors",
                "--allow-running-insecure-content",
                "--allow-insecure-localhost",
                "--disable-gpu"));

        if (headless.equals("true")) {
            arguments.add("--headless");
            arguments.add("--disable-dev-shm-usage");
            arguments.add("--no-sandbox");
        }

        switch (platform) {
            case "IE":
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.requireWindowFocus();
                options.destructivelyEnsureCleanSession();
                browserDriver = new InternetExplorerDriver(options);
                break;
            case "Chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(arguments);
                // chromeOptions.addArguments("--whitelisted-ips=''");
                browserDriver = new ChromeDriver(chromeOptions);
                break;
            case "Firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(arguments);
                // firefoxOptions.addArguments("--whitelisted-ips=''");
                browserDriver = new FirefoxDriver(firefoxOptions);
                break;
            case "Edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments(arguments);
                // edgeOptions.addArguments("--whitelisted-ips=''");
                browserDriver = new EdgeDriver(edgeOptions);
                break;
            case "Safari":
                SafariOptions safariOptions = new SafariOptions();
                browserDriver = new SafariDriver(safariOptions);
                break;
            default:
                System.out.println("[Driver] - No driver browser");
                break;
        }

        System.out.println("-------------------------------------------------------");
        System.out
                .println("Browser Version: " + ((RemoteWebDriver) browserDriver).getCapabilities().getBrowserVersion());
        System.out.println("-------------------------------------------------------");

        browserDriver.manage().window().maximize();
        browserDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        browserDriver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(browserDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    @After
    public void stop(Scenario scenario) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Scenario: " + scenario.getName());
        System.out.println("Status: " + scenario.getStatus().toString());
        System.out.println("-------------------------------------------------------");

        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) Core.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Attach Screenshot");
        }

        if (browserDriver != null)
            browserDriver.quit();
    }

    public static WebDriver getDriver() {
        return browserDriver;
    }
}