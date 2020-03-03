package org.kie.kogito.tooling;

import java.io.File;
import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SimpleIT {

    private static final String CHROME_EXTENSION_ZIP_PROPERTY = "chromeExtensionZip",
            EVALUATION_EXAMPLE_URL = "https://github.com/kiegroup/kie-wb-playground/blob/master/evaluation/src/main/resources/";

    private WebDriver chromeDriver;

    @BeforeEach
    public void createDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        String chromeExtensionZip = System.getProperty(CHROME_EXTENSION_ZIP_PROPERTY);
        if (chromeExtensionZip == null) {
            throw new RuntimeException("Please set " + CHROME_EXTENSION_ZIP_PROPERTY + " property to zipped Kogito tooling distribution.");
        }
        File chromeExtensionZipFile = new File(chromeExtensionZip);
        chromeOptions.addExtensions(chromeExtensionZipFile);
        chromeDriver = new ChromeDriver(chromeOptions);
    }

    @AfterEach
    public void teardown() {
        if (chromeDriver != null) {
            chromeDriver.quit();
        }
    }

    @Test
    public void openGoogleTest() throws InterruptedException, IOException {
        chromeDriver.get(EVALUATION_EXAMPLE_URL);
        chromeDriver.findElement(By.linkText("evaluation.bpmn")).click();

//        Files.write(Paths.get("target", "screen.html"), chromeDriver.getPageSource().getBytes());
//        File screenshotAs = ((TakesScreenshot) chromeDriver).getScreenshotAs(OutputType.FILE);
//        Files.copy(screenshotAs.toPath(), Paths.get("target", "aaa"), StandardCopyOption.REPLACE_EXISTING);

        By kogitoFrame = By.className("kogito-iframe");
        new WebDriverWait(chromeDriver, 2).until(ExpectedConditions.presenceOfElementLocated(kogitoFrame));

        chromeDriver.switchTo().frame(chromeDriver.findElement(kogitoFrame));

        By explorerIcon = By.className("fa-eye");
        new WebDriverWait(chromeDriver, 10).until(ExpectedConditions.presenceOfElementLocated(explorerIcon));

        chromeDriver.switchTo().defaultContent();

        WebElement sourceView = chromeDriver.findElement(By.className("js-file-line-container"));
        WebElement kogitoView = chromeDriver.findElement(kogitoFrame);

        KogitoButtons kogitoButtons = PageFactory.initElements(chromeDriver, KogitoButtons.class);
        kogitoButtons.seeAsSource();
        Assertions.assertThat(kogitoView.isDisplayed()).isFalse();
        Assertions.assertThat(sourceView.isDisplayed()).isTrue();

        kogitoButtons.seeAsDiagram();
        Assertions.assertThat(kogitoView.isDisplayed()).isTrue();
        Assertions.assertThat(sourceView.isDisplayed()).isFalse();
    }
}
