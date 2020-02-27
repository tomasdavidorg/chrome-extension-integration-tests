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
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
//        chromeOptions.addExtensions(new File("/home/tdavid/Data/Projects/kiegroup/kogito-tooling/packages/chrome-extension-pack-kogito-kie-editors/dist/chrome_extension_kogito_kie_editors_0.2.8/dist.crx"));
        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);

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
        //chromeDriver.findElement(By.className("kogito-button")).click();

        Thread.sleep(20000);
    }
}
