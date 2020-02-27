package org.kie.kogito.tooling;

import java.io.File;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SimpleIT {

    private WebDriver driver;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void createDriver() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void openGoogleTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/home/tdavid/Downloads/chromedriver_linux64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("/home/tdavid/Data/Projects/kiegroup/kogito-tooling/packages/chrome-extension-pack-kogito-kie-editors/dist/chrome_extension_kogito_kie_editors_0.2.8/dist.crx"));
        ChromeDriver chromeDriver = new ChromeDriver(options);
//        chromeDriver.set
        chromeDriver.get("https://github.com/kiegroup/kie-wb-playground/blob/master/evaluation/src/main/resources/");
        chromeDriver.findElement(By.linkText("evaluation.bpmn")).click();

        new WebDriverWait(chromeDriver, 5);
//        ExpectedConditions.alertIsPresent().
//                new Wait<>()
//        for (int i = 0; i< 5; i++) {
//            W
//            boolean displayed = chromeDriver.findElement(By.id("loading-screen")).isDisplayed();
//
//            System.out.println("###" + displayed);
//        }

        Thread.sleep(5000);
        chromeDriver.findElement(By.className("kogito-button")).click();

        // WebElement searchBox = firefoxDriver.findElement(By.id("lst-ib"));

        //searchBox.sendKeys("Dog");

        Thread.sleep(20000);

        //System.out.println("Page title is: " + firefoxDriver.getTitle());

        chromeDriver.quit();

//        System.setProperty("webdriver.chrome.logfile", "aaa.log");
//        System.setProperty("webdriver.chrome.verboseLogging", "true");
//        System.setProperty("webdriver.chrome.driver", "/home/tdavid/Downloads/chromedriver");
////        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
//
//        ChromeOptions opt = new ChromeOptions();
////        opt.setCapability("w3c", false);
//        ChromeDriver driver = new ChromeDriver(opt);
//
//        driver.manage().logs().get(LogType.CLIENT);
//        driver.get("http://www.google.com");
//
//        WebElement searchBox = driver.findElement(By.id("gsr"));
//
//        searchBox.sendKeys("Dog");
//
//        Thread.sleep(10000);
//
//        System.out.println("Page title is: " + driver.getTitle());
//
//        driver.quit();
    }
}
