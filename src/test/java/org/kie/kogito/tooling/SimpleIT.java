package org.kie.kogito.tooling;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleIT {

    private static final String CHROME_EXTENSION_ZIP_PROPERTY = "chromeExtensionZip",
            EVALUATION_EXAMPLE_URL = "https://github.com/kiegroup/kie-wb-playground/blob/master/evaluation/src/main/resources/";

    private WebDriver chromeDriver;

    @BeforeEach
    public void setupDriver() {
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
    public void teardown() throws IOException {
        makeScreenshots();

        if (chromeDriver != null) {
            chromeDriver.quit();
        }
    }

    @Test
    public void openGoogleTest() throws InterruptedException, IOException, UnsupportedFlavorException {

        // open Evaluation example BPMN file
        chromeDriver.get(EVALUATION_EXAMPLE_URL);
        assertThat(isElementPresent(By.xpath("//div[starts-with(@id, 'external_editor')]/a"))).isTrue();
        chromeDriver.findElement(By.linkText("evaluation.bpmn")).click();

        // switch to Kogito frame
        By kogitoFrame = By.className("kogito-iframe");
        new WebDriverWait(chromeDriver, 2).until(ExpectedConditions.presenceOfElementLocated(kogitoFrame));
        chromeDriver.switchTo().frame(chromeDriver.findElement(kogitoFrame));

        // wait until editor is loaded
        By explorerIcon = By.className("fa-eye");
        new WebDriverWait(chromeDriver, 30).until(ExpectedConditions.presenceOfElementLocated(explorerIcon));

        // switch back to default content
        chromeDriver.switchTo().defaultContent();

        WebElement sourceView = chromeDriver.findElement(By.className("js-file-line-container"));
        WebElement kogitoView = chromeDriver.findElement(kogitoFrame);

        KogitoButtons kogitoButtons = PageFactory.initElements(chromeDriver, KogitoButtons.class);

        kogitoButtons.copyLinkToOnlineEditor();
        String clipboardContent = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        assertThat(clipboardContent).startsWith("https://kiegroup.github.io");

        kogitoButtons.seeAsSource();
        assertThat(kogitoView.isDisplayed()).isFalse();
        assertThat(sourceView.isDisplayed()).isTrue();

        kogitoButtons.seeAsDiagram();
        assertThat(kogitoView.isDisplayed()).isTrue();
        assertThat(sourceView.isDisplayed()).isFalse();

        kogitoButtons.openOnlineEditor();

        new WebDriverWait(chromeDriver, 15).until(chromeDriver -> chromeDriver.getWindowHandles().size() == 2);
        chromeDriver.switchTo().window((String) chromeDriver.getWindowHandles().toArray()[1]);

        chromeDriver.switchTo().defaultContent();

        new WebDriverWait(chromeDriver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='evaluation.bpmn']")));
    }

    private boolean isElementPresent(By by) {
        try {
            chromeDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void makeScreenshots() throws IOException {
        final String targetDir = "target",
                screenshotsDir = "screenshots",
                htmlScreenshot = "screen.html",
                pngScreenshot = "screen.png";

        Path screenshotsPath = Paths.get(targetDir, screenshotsDir);
        Files.createDirectory(screenshotsPath);

        // create html screenshot
        Files.write(Paths.get(targetDir, screenshotsDir, htmlScreenshot), chromeDriver.getPageSource().getBytes());

        // create png screenshot
        File screenshot = ((TakesScreenshot) chromeDriver).getScreenshotAs(OutputType.FILE);
        Files.copy(screenshot.toPath(), Paths.get(targetDir, screenshotsDir, pngScreenshot));
    }
}
