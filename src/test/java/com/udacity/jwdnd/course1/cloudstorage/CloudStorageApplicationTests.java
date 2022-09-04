package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

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
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) throws InterruptedException {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
		Thread.sleep(3500);

	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	private void doLogOut(){
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();
	}

	private void stepForTestingNoteFunctionSim(){
		doLogIn("testAddNote", "123");
		WebElement noteTabButton = driver.findElement(By.id("nav-notes-tab"));
		noteTabButton.click();
	}

	private void stepForTestingCredentialFunctionSim(){
		doLogIn("testAddCredential", "123");
		WebElement credentialTabButton = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabButton.click();
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	@Order(1)
	public void testRedirection() throws InterruptedException {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.

		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * Test access to any url of the system after logging out
	 */
	@Test
	@Order(2)
	public void testAccessAfterLoggingOut() throws InterruptedException {
		doMockSignUp("AccessAfterLoggingOut","Test","AccessAfterLoggingOut","123");
		doLogIn("AccessAfterLoggingOut", "123");
		doLogOut();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * Test access to home page after logging in
	 */
	@Test
	@Order(3)
	public void testAccessAfterLoggingIn() throws InterruptedException {
		doMockSignUp("testAccessAfterLoggingIn","Test","testAccessAfterLoggingIn","123");
		doLogIn("testAccessAfterLoggingIn", "123");
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	@Order(4)
	public void testBadUrl() throws InterruptedException {
		// Create a test account
		doMockSignUp("testBadUrl","Test","testBadUrl","123");
		doLogIn("testBadUrl", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	@Order(5)
	public void testLargeUpload() throws InterruptedException {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/**
	 * Test create note and verify that the note details are visible in the note list
	 */
	@Test
	@Order(6)
	public void testAddNote() throws InterruptedException {
		doMockSignUp("testAddNote","Test","testAddNote","123");
		doLogIn("testAddNote", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 1);

		WebElement noteTabButton = driver.findElement(By.id("nav-notes-tab"));
		noteTabButton.click();
		Thread.sleep(500);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-note-button")));
		WebElement addNoteButton = driver.findElement(By.id("add-new-note-button"));
		addNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputNoteTitle = driver.findElement(By.id("note-title"));
		inputNoteTitle.click();
		inputNoteTitle.sendKeys("Note title adding");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputNoteDescription = driver.findElement(By.id("note-description"));
		inputNoteDescription.click();
		inputNoteDescription.sendKeys("Note description adding");

		WebElement saveNoteButton = driver.findElement(By.id("save-note-button"));
		saveNoteButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("Success"));

		WebElement continueButton = driver.findElement(By.id("result-continue-link"));
		continueButton.click();

		WebElement noteTabButtonAfter = driver.findElement(By.id("nav-notes-tab"));
		noteTabButtonAfter.click();

		Assertions.assertTrue(driver.getPageSource().contains("Note title adding"));
		Assertions.assertTrue(driver.getPageSource().contains("Note description adding"));

	}

	/**
	 * Test edit note and verify that the note details are visible in the note list
	 */

	@Test
	@Order(7)
	public void testEditNote() throws InterruptedException {
		// testAddNote(); // If you run all Test simultaneously, comment this line
		stepForTestingNoteFunctionSim(); // An uncomment this

		WebDriverWait webDriverWait = new WebDriverWait(driver, 1);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note-1")));
		WebElement editNoteButton = driver.findElement(By.id("edit-note-1"));
		editNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputNoteTitle = driver.findElement(By.id("note-title"));
		inputNoteTitle.click();
		inputNoteTitle.clear();
		inputNoteTitle.sendKeys("Note title editing");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputNoteDescription = driver.findElement(By.id("note-description"));
		inputNoteDescription.click();
		inputNoteDescription.clear();
		inputNoteDescription.sendKeys("Note description editing");

		WebElement saveNoteButton = driver.findElement(By.id("save-note-button"));
		saveNoteButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("Success"));

		WebElement continueButton = driver.findElement(By.id("result-continue-link"));
		continueButton.click();

		WebElement noteTabButtonAfter = driver.findElement(By.id("nav-notes-tab"));
		noteTabButtonAfter.click();

		Assertions.assertTrue(driver.getPageSource().contains("Note title editing"));
		Assertions.assertTrue(driver.getPageSource().contains("Note description editing"));
	}

	/**
	 * Test delete note and verify that the note no longer appears in the note list
	 */
	@Test
	@Order(8)
	public void testDeleteNote() throws InterruptedException {
		// testAddNote(); // If you run all Test simultaneously, comment this line
		stepForTestingNoteFunctionSim(); // An uncomment this

		WebDriverWait webDriverWait = new WebDriverWait(driver, 1);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note-1")));
		WebElement deleteNoteButton = driver.findElement(By.id("delete-note-1"));
		deleteNoteButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("Success"));

		WebElement continueButton = driver.findElement(By.id("result-continue-link"));
		continueButton.click();

		WebElement noteTabButtonAfter = driver.findElement(By.id("nav-notes-tab"));
		noteTabButtonAfter.click();

		Assertions.assertFalse(driver.getPageSource().contains("Note title adding"));
		Assertions.assertFalse(driver.getPageSource().contains("Note description adding"));
	}

	/**
	 * Test create credential and verify that the credential details are visible in the credential list
	 */
	@Test
	@Order(9)
	public void testAddCredential() throws InterruptedException {
		doMockSignUp("testAddCredential","Test","testAddCredential","123");
		doLogIn("testAddCredential", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 1);

		WebElement credentialTabButton = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabButton.click();

		Thread.sleep(500);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-button")));
		WebElement addCredentialButton = driver.findElement(By.id("add-credential-button"));
		addCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputCredentialUrl = driver.findElement(By.id("credential-url"));
		inputCredentialUrl.click();
		inputCredentialUrl.sendKeys("Credential url adding");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputCredentialUsername = driver.findElement(By.id("credential-username"));
		inputCredentialUsername.click();
		inputCredentialUsername.sendKeys("Credential username adding");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputCredentialPassword = driver.findElement(By.id("credential-password"));
		inputCredentialPassword.click();
		inputCredentialPassword.sendKeys("Credential password adding");

		WebElement saveCredentialButton = driver.findElement(By.id("save-credential-button"));
		saveCredentialButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("Success"));

		WebElement continueButton = driver.findElement(By.id("result-continue-link"));
		continueButton.click();

		WebElement credentialTabButtonAfter = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabButtonAfter.click();
		Assertions.assertTrue(driver.getPageSource().contains("Credential url adding"));
		Assertions.assertTrue(driver.getPageSource().contains("Credential username adding"));
	}

	/**
	 * Test edit credential and verify that the credential details are visible in the credential list
	 */
	@Test
	@Order(10)
	public void testEditCredential() throws InterruptedException {
		// testAddCredential(); // If you run all Test simultaneously, comment this line
		stepForTestingCredentialFunctionSim(); // And uncomment this
		WebDriverWait webDriverWait = new WebDriverWait(driver, 1);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential-1")));
		WebElement addCredentialButton = driver.findElement(By.id("edit-credential-1"));
		addCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputCredentialUrl = driver.findElement(By.id("credential-url"));
		inputCredentialUrl.click();
		inputCredentialUrl.clear();
		inputCredentialUrl.sendKeys("Credential url editing");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputCredentialUsername = driver.findElement(By.id("credential-username"));
		inputCredentialUsername.click();
		inputCredentialUsername.clear();
		inputCredentialUsername.sendKeys("Credential username editing");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputCredentialPassword = driver.findElement(By.id("credential-password"));
		inputCredentialPassword.click();
		inputCredentialPassword.clear();
		inputCredentialPassword.sendKeys("Credential password editing");

		WebElement saveCredentialButton = driver.findElement(By.id("save-credential-button"));
		saveCredentialButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("Success"));

		WebElement continueButton = driver.findElement(By.id("result-continue-link"));
		continueButton.click();

		WebElement credentialTabButtonAfter = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabButtonAfter.click();
		Assertions.assertTrue(driver.getPageSource().contains("Credential url editing"));
		Assertions.assertTrue(driver.getPageSource().contains("Credential username editing"));
	}

	/**
	 * Test delete credential and verify that the credential no longer appears in the note list
	 */
	@Test
	@Order(11)
	public void testDeleteCredential() throws InterruptedException {
		// testAddCredential(); // If you run all Test simultaneously, comment this line
		stepForTestingCredentialFunctionSim(); // And uncomment this
		WebDriverWait webDriverWait = new WebDriverWait(driver, 1);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential-1")));
		WebElement deleteCredentialButton = driver.findElement(By.id("delete-credential-1"));
		deleteCredentialButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("Success"));

		WebElement continueButton = driver.findElement(By.id("result-continue-link"));
		continueButton.click();

		WebElement credentialTabButton = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabButton.click();

		Assertions.assertFalse(driver.getPageSource().contains("Credential url adding"));
		Assertions.assertFalse(driver.getPageSource().contains("Credential username adding"));
	}
}
