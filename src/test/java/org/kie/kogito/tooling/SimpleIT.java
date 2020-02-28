package org.kie.kogito.tooling;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SimpleIT {

    private WebDriver chromeDriver;

    @BeforeEach
    public void createDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        File kogitoPlugin = new File(getClass().getClassLoader().getResource("dist.crx").getFile());
        chromeOptions.addExtensions(kogitoPlugin);
        chromeDriver = new ChromeDriver(chromeOptions);
    }

    @AfterEach
    public void teardown() {
        if (chromeDriver != null) {
            chromeDriver.quit();
        }
    }

    @Test
    public void openGoogleTest() throws InterruptedException {
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
        KogitoButtons kogitoButtons = PageFactory.initElements(chromeDriver, KogitoButtons.class);
        kogitoButtons.seeAsSource();
        Assertions.assertThat(chromeDriver.findElement(By.className("kogito-iframe-container")).isDisplayed()).isFalse();
        Assertions.assertThat(chromeDriver.findElement(By.className("js-file-line-container")).isDisplayed()).isTrue();

        kogitoButtons.seeAsDiagram();
        Assertions.assertThat(chromeDriver.findElement(By.className("kogito-iframe-container")).isDisplayed()).isTrue();
        Assertions.assertThat(chromeDriver.findElement(By.className("js-file-line-container")).isDisplayed()).isFalse();
//        Assertions.assertThat()

       // chromeDriver.findElement(By.className("kogito-button")).click();

        Thread.sleep(20000);
    }
}
