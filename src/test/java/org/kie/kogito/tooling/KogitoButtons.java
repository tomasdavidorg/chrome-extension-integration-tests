package org.kie.kogito.tooling;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class KogitoButtons {

    @FindBy(xpath = "//button[@data-testid='see-as-diagram-button']")
    private WebElement seeAsDiagramButton;

    @FindBy(xpath = "//button[@data-testid='open-ext-editor-button']")
    private WebElement openInOnlineEditorButton;

    @FindBy(xpath = "//button[@data-testid='copy-link-button']")
    private WebElement copyLinkButton;

    @FindBy(xpath = "//button[@data-testid='see-as-source-button']")
    private WebElement seeAsSourceButton;

    @FindBy(xpath = "//button[@data-testid='go-fullscreen-button']")
    private WebElement fullScreenButton;

    public void seeAsSource() {
        seeAsSourceButton.click();
    }

    public void seeAsDiagram() {
        seeAsDiagramButton.click();
    }

    public void copyLinkToOnlineEditor() {
        copyLinkButton.click();
    }

    public void openOnlineEditor() {
        openInOnlineEditorButton.click();
    }

    public void fullScreen() {
        fullScreenButton.click();
    }
}
