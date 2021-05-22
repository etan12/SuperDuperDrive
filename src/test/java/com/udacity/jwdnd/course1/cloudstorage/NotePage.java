package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NotePage {

    @FindBy(css = "#nav-notes-tab")
    private WebElement noteTab;

    @FindBy(css = "#newNoteButton")
    private WebElement newNoteButton;

    @FindBy(css = "#note-title")
    private WebElement noteTitle;

    @FindBy(css = "#note-description")
    private WebElement noteDescription;

    @FindBy(css = "#noteSubmitButton")
    private WebElement noteSubmitButton;

    @FindBy(css = "#noteEditButton")
    private WebElement noteEditButton;

    @FindBy(css = "#noteDeleteButton")
    private WebElement noteDeleteButton;

    @FindBy(css = "#noteTitleDisplay")
    private WebElement noteTitleDisplay;

    @FindBy(css = "#noteDescriptionDisplay")
    private WebElement noteDescriptionDisplay;

    @FindBy(id = "noteRows")
    private WebElement noteRows;


    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public boolean noteTitleExists(String noteTitle) {
        for (WebElement webElement : noteRows.findElements(By.tagName("th"))) {
            if (webElement.getAttribute("innerHTML").equals(noteTitle)) {
                return true;
            }
        }
        return false;
    }

    public boolean noteDescriptionExists(String noteDescription) {
        for (WebElement webElement : noteRows.findElements(By.tagName("td"))) {
            if (webElement.getAttribute("innerHTML").equals(noteDescription)) {
                return true;
            }
        }
        return false;
    }

    public WebElement getNoteTab() {
        return noteTab;
    }

    public WebElement getNewNoteButton() {
        return newNoteButton;
    }

    public WebElement getNoteTitle() {
        return noteTitle;
    }

    public WebElement getNoteDescription() {
        return noteDescription;
    }

    public WebElement getNoteSubmitButton() {
        return noteSubmitButton;
    }

    public WebElement getNoteTitleDisplay() {
        return noteTitleDisplay;
    }

    public WebElement getNoteDescriptionDisplay() {
        return noteDescriptionDisplay;
    }

    public WebElement getNoteEditButton() {
        return noteEditButton;
    }

    public WebElement getNoteDeleteButton() {
        return noteDeleteButton;
    }

    public WebElement getNoteRows() {
        return noteRows;
    }
}
