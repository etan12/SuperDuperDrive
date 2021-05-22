package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private EncryptionService encryptionService;

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		// Log out user every JUnit test if logged in
		driver.get("http://localhost:" + this.port + "/home");
		if (driver.getCurrentUrl().equals("http://localhost:" + this.port + "/home")) {
			HomePage homePage = new HomePage(driver);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", homePage.getLogoutButton());
		}

		if (this.driver != null) {
			driver.quit();
		}
	}

	// Helper method to signup new user and log in
	public void signupAndLoginUser() {
		// User signs up
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		String generatedUsername = String.valueOf(Math.random());
		String password = String.valueOf(Math.random());

		signupPage.signup("John", "Doe", generatedUsername, password);
		signupPage.clickSignupButton();

		// User navigates to /login
		driver.get("http://localhost:" + this.port + "/login");

		// User log ins
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(generatedUsername, password);
		loginPage.clickLoginButton();
	}

	@Test
	public void getLoginPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// Write a Selenium test that verifies that the home page is not accessible without logging in.
	@Test
	public void testLoginAccessibility() {
		// User navigating to /home will be redirected to /login if not logged in
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		// User can access signup page
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		// User can access login page
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/*
	Write a Selenium test that signs up a new user, logs that user in,
	verifies that they can access the home page, then logs out
	and verifies that the home page is no longer accessible.
	 */
	@Test
	public void testUserLogin() throws InterruptedException {
		// User navigating to /home will be redirected to /login because he/she is not logged in
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		this.signupAndLoginUser();

		// User is redirected to /home
		Assertions.assertEquals("Home", driver.getTitle());

		// User logs out
		HomePage homePage = new HomePage(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", homePage.getLogoutButton());

		// User tries to go to /home but is redirected to /login because he/she is logged out
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/*
	Write a Selenium test that logs in an existing user, creates a note and verifies that the note details are visible in the note list.
	 */
	@Test
	public void testCreateNote() throws InterruptedException {
		// Sign up and log in user
		this.signupAndLoginUser();
		NotePage note = new NotePage(driver);

		// User navigates to Notes tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteTab());

		// User clicks Add a New Note Button
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNewNoteButton());

		// User creates new note
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'noteTitle';", note.getNoteTitle());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'noteDescription';", note.getNoteDescription());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteSubmitButton());

		// Verify that User is on /home
		Assertions.assertEquals("Home", driver.getTitle());

		// User navigates to Notes tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteTab());

		// Verify that the note (with title and description) was added in the Notes tab
		Assertions.assertEquals(true, note.noteTitleExists("noteTitle"));
		Assertions.assertEquals(true, note.noteDescriptionExists("noteDescription"));
	}

	/*
	Write a Selenium test that logs in an existing user with existing notes,
	clicks the edit note button on an existing note, changes the note data,
	saves the changes, and verifies that the changes appear in the note list.
	 */
	@Test
	public void testUpdateNote() throws InterruptedException {
		// Sign up and log in user
		this.signupAndLoginUser();
		NotePage note = new NotePage(driver);

		// User navigates to Notes tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteTab());

		// User clicks Add a New Note Button
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNewNoteButton());

		// User creates new note
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'noteTitle';", note.getNoteTitle());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'noteDescription';", note.getNoteDescription());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteSubmitButton());

		// Verify that User is on /home
		Assertions.assertEquals("Home", driver.getTitle());

		// User navigates to Notes tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteTab());

		// User clicks on an Edit button in Notes tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteEditButton());

		// User edits note
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'updateNoteTitle';", note.getNoteTitle());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'updateNoteDescription';", note.getNoteDescription());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteSubmitButton());

		// Verify that the note (with title and description) was added in the Notes tab
		Assertions.assertEquals(true, note.noteTitleExists("updateNoteTitle"));
		Assertions.assertEquals(true, note.noteDescriptionExists("updateNoteDescription"));
	}

	/*
	Write a Selenium test that logs in an existing user with existing notes,
	clicks the delete note button on an existing note,
	and verifies that the note no longer appears in the note list.
	 */
	@Test
	public void testDeleteNote() {
		// Sign up and log in user
		this.signupAndLoginUser();
		NotePage note = new NotePage(driver);

		// User navigates to Notes tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteTab());

		// User clicks Add a New Note Button
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNewNoteButton());

		// User creates new note
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'noteTitle';", note.getNoteTitle());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'noteDescription';", note.getNoteDescription());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteSubmitButton());

		// Verify that User is on /home
		Assertions.assertEquals("Home", driver.getTitle());

		// User navigates to Notes tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteTab());

		// User clicks on a Delete button in Notes tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", note.getNoteDeleteButton());

		// Verify that there are no Notes left in the Notes tab
		Assertions.assertThrows(NoSuchElementException.class, ()-> {
			note.noteTitleExists("noteTitle");
		});
	}

	/*
	Write a Selenium test that logs in an existing user,
	creates a credential and verifies that the credential details are visible in the credential list.
	 */
	@Test
	public void testCreateCredential() {
		// Sign up and log in user
		this.signupAndLoginUser();

		CredentialPage credential = new CredentialPage(driver);

		// User navigates to Credential tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialTab());

		// User clicks Add a Credential Button
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getNewCredentialButton());

		// User creates new credential
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialURL';", credential.getCredentialUrl());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialUsername';", credential.getCredentialUsername());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialPassword';", credential.getCredentialPassword());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialSubmitButton());

		// Verify that User is on /home
		Assertions.assertEquals("Home", driver.getTitle());

		// User navigates to Credential tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialTab());

		// Verify that the credential was added in the Credential tab
		Assertions.assertEquals(true, credential.credentialUrlExists("credentialURL"));
		Assertions.assertEquals(true, credential.credentialUsernameExists("credentialUsername"));
	}

	/*
	Write a Selenium test that logs in an existing user with existing credentials,
	clicks the edit credential button on an existing credential,
	changes the credential data, saves the changes, and verifies that the changes appear in the credential list.
	 */
	@Test
	public void testUpdateCredential() {
		// Sign up and log in user
		this.signupAndLoginUser();

		CredentialPage credential = new CredentialPage(driver);

		// User navigates to Credential tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialTab());

		// User clicks Add a Credential Button
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getNewCredentialButton());

		// User creates new credential
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialURL';", credential.getCredentialUrl());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialUsername';", credential.getCredentialUsername());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialPassword';", credential.getCredentialPassword());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialSubmitButton());

		// Verify that User is on /home
		Assertions.assertEquals("Home", driver.getTitle());

		// User navigates to Credential tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialTab());

		// User clicks on an Edit button in Credential tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialEditButton());

		// Grab credentialId of edited credential for decryption test
		String credentialId = credential.getHiddenCredentialId().getAttribute("value");

		// Grab the Credential from the DB
		Credential credentialInDb = credentialService.getCredentialByCredentialId(Integer.valueOf(credentialId));

		/*
		Verify that the viewable password is unencrypted
		Take the key from the credentialInDb and the password in input field with id of "credential-password",
		call encryptionService's encryptValue to get the encrypted password.
		Unencrypted password in input field with id of "credential-password" + key from DB = password stored in DB
		 */
		Assertions.assertEquals(encryptionService.encryptValue(credential.getCredentialPassword().getAttribute("value"), credentialInDb.getKey()),
				credentialInDb.getPassword());

		// User edits credential
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'updateCredentialURL';", credential.getCredentialUrl());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'updateCredentialUsername';", credential.getCredentialUsername());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'updateCredentialPassword';", credential.getCredentialPassword());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialSubmitButton());

		// Verify that the credential was added in the Credential tab
		Assertions.assertEquals(true, credential.credentialUrlExists("updateCredentialURL"));
		Assertions.assertEquals(true, credential.credentialUsernameExists("updateCredentialUsername"));

		// Grab the Credential from the DB
		credentialInDb = credentialService.getCredentialByCredentialId(Integer.valueOf(credentialId));

		/*
		Verify that the displayed password is encrypted.
		Take the currently displayed encrypted password and the key stored off in the database for this credential,
		decrypt the password using encryptionService's decryptValue, and compare it to what the User just entered ("updateCredentialPassword").
		 */
		Assertions.assertEquals(encryptionService.decryptValue(credential.getCredentialPasswordDisplay().getAttribute("innerHTML"), credentialInDb.getKey()),
				"updateCredentialPassword");
	}

	/*
	Write a Selenium test that logs in an existing user with existing credentials,
	clicks the delete credential button on an existing credential,
	and verifies that the credential no longer appears in the credential list.
	 */
	@Test
	public void testDeleteCredential() {
		// Sign up and log in user
		this.signupAndLoginUser();

		CredentialPage credential = new CredentialPage(driver);

		// User navigates to Credential tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialTab());

		// User clicks Add a Credential Button
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getNewCredentialButton());

		// User creates new credential
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialURL';", credential.getCredentialUrl());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialUsername';", credential.getCredentialUsername());
		((JavascriptExecutor) driver).executeScript("arguments[0].value = 'credentialPassword';", credential.getCredentialPassword());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialSubmitButton());

		// Verify that User is on /home
		Assertions.assertEquals("Home", driver.getTitle());

		// User navigates to Credential tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialTab());

		// User clicks on a Delete button in Credential tab
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", credential.getCredentialDeleteButton());

		// Verify that there are no Credentials left in the Credential tab
		Assertions.assertThrows(NoSuchElementException.class, ()-> {
			credential.credentialUrlExists("credentialURL");
		});
	}
}