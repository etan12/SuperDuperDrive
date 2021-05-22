package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {
    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(css = "#newCredentialButton")
    private WebElement newCredentialButton;

    @FindBy(css = "#credential-url")
    private WebElement credentialUrl;

    @FindBy(css = "#credential-username")
    private WebElement credentialUsername;

    @FindBy(css = "#credential-password")
    private WebElement credentialPassword;

    @FindBy(css = "#credentialSubmitButton")
    private WebElement credentialSubmitButton;

    @FindBy(css = "#credentialUrlDisplay")
    private WebElement credentialUrlDisplay;

    @FindBy(css = "#credentialUsernameDisplay")
    private WebElement credentialUsernameDisplay;

    @FindBy(css = "#credentialPasswordDisplay")
    private WebElement credentialPasswordDisplay;

    @FindBy(id = "credentialRows")
    private WebElement credentialRows;

    @FindBy(css = "#credentialEditButton")
    private WebElement credentialEditButton;

    @FindBy(css = "#credentialDeleteButton")
    private WebElement credentialDeleteButton;

    @FindBy(css = "#credential-id")
    private WebElement hiddenCredentialId;

    public boolean credentialUrlExists(String credentialUrl) {
        for (WebElement webElement : credentialRows.findElements(By.id("credentialUrlDisplay"))) {
            if (webElement.getAttribute("innerHTML").equals(credentialUrl)) {
                return true;
            }
        }
        return false;
    }

    public boolean credentialUsernameExists(String credentialUsername) {
        for (WebElement webElement : credentialRows.findElements(By.id("credentialUsernameDisplay"))) {
            if (webElement.getAttribute("innerHTML").equals(credentialUsername)) {
                return true;
            }
        }
        return false;
    }

    public boolean credentialPasswordExists(String credentialPassword) {
        for (WebElement webElement : credentialRows.findElements(By.id("credentialPasswordDisplay"))) {
            if (webElement.getAttribute("innerHTML").equals(credentialPassword)) {
                return true;
            }
        }
        return false;
    }

    public WebElement getCredentialTab() {
        return credentialTab;
    }

    public WebElement getNewCredentialButton() {
        return newCredentialButton;
    }

    public WebElement getCredentialUrl() {
        return credentialUrl;
    }

    public WebElement getCredentialUsername() {
        return credentialUsername;
    }

    public WebElement getCredentialPassword() {
        return credentialPassword;
    }

    public WebElement getCredentialSubmitButton() {
        return credentialSubmitButton;
    }

    public CredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getCredentialUrlDisplay() {
        return credentialUrlDisplay;
    }

    public WebElement getCredentialUsernameDisplay() {
        return credentialUsernameDisplay;
    }

    public WebElement getCredentialPasswordDisplay() {
        return credentialPasswordDisplay;
    }

    public WebElement getCredentialRows() {
        return credentialRows;
    }

    public WebElement getCredentialEditButton() {
        return credentialEditButton;
    }

    public WebElement getCredentialDeleteButton() {
        return credentialDeleteButton;
    }

    public WebElement getHiddenCredentialId() {
        return hiddenCredentialId;
    }
}
