package org.kie.kogito.tooling;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SimpleIT {

    private WebDriver driver;

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
        File kogitoPlugin = new File(getClass().getClassLoader().getResource("dist.crx").getFile());
        //chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920x1080");
        chromeOptions.addExtensions(kogitoPlugin);
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
        chromeDriver.findElement(By.className("kogito-button")).click();

        Thread.sleep(20000);
    }
}
