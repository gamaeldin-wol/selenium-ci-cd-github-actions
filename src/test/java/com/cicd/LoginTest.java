package com.cicd;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Detect CI environment — run headless when no display is available
        String ci = System.getenv("CI");
        if (ci != null && ci.equals("true")) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginPageTitle() {
        driver.get("https://the-internet.herokuapp.com/login");
        WebElement heading = driver.findElement(By.tagName("h2"));
        String actualHeading = heading.getText();
        Assert.assertEquals(actualHeading, "Login Page",
                "Login page heading should be 'Login Page'");
    }

    @Test
    public void testSuccessfulLogin() {
        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Wait for flash element to be present after login
        WebElement flashElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash")));
        String flashMessage = flashElement.getText();
        Assert.assertTrue(flashMessage.contains("You logged into a secure area!"),
                "Success message should appear after valid login");
    }

    @Test
    public void testFailedLogin() {
        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("invalidUser");
        driver.findElement(By.id("password")).sendKeys("wrongPassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Wait for flash element to be present after failed login attempt
        WebElement flashElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash")));
        String flashMessage = flashElement.getText();
        Assert.assertTrue(flashMessage.contains("Your username is invalid!"),
                "Error message should appear after invalid login");
    }
}
