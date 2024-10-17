package test;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup(); // Ensure WebDriver binaries are setup
		ChromeOptions options = new ChromeOptions();

		// Common ChromeOptions methods:
		options.addArguments("--start-maximized"); // Open Chrome maximized
		options.addArguments("--disable-notifications"); // Disable browser notifications
		options.addArguments("--incognito"); // Launch in incognito mode
		//options.addArguments("--headless"); // Run in headless mode (without GUI)
		options.addArguments("--disable-infobars");// Disable the "Chrome is being controlled by automated test software" infobar
		
		/*options.addArguments("--disable-gpu"); // Disable GPU acceleration (useful for some systems)
		options.addArguments("--no-sandbox"); // Bypass OS security model (useful in Docker)
		options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems in Docker
		options.addArguments("--remote-allow-origins=*"); // Allow remote origins, useful for CORS

		// Set the window size (important for headless mode to mimic a regular browser)
		options.addArguments("--window-size=1920,1080");

		// Disable browser extensions for better stability
		options.addArguments("--disable-extensions");

		// Allow browser to run insecure content (useful when testing HTTPS-related scenarios)
		options.setAcceptInsecureCerts(true);

		// Set proxy settings (if required)
		Proxy proxy = new Proxy();
		proxy.setHttpProxy("myhttpproxy:3337");
		options.setProxy(proxy);

		// Set user agent (useful for mobile testing or impersonating another browser)
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

		// Enable verbose logging for debugging purposes
		options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
		options.addArguments("--enable-logging");
		options.addArguments("--v=1"); // Increase verbosity of logging

		// Add experimental options (used for more advanced configuration)
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_setting_values.cookies", 2); // Block cookies
		prefs.put("download.default_directory", "/path/to/download/folder"); // Set download folder
		options.setExperimentalOption("prefs", prefs);

		// Set binary path for custom Chrome installations
		// options.setBinary("/path/to/chrome");*/

		driver = new ChromeDriver(options); // Initialize WebDriver with options
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

	}

	@Test(enabled = true)
	public void baseTest() {
		// Navigate to the DemoQA home page
		driver.get("https://demoqa.com");
		System.out.println("Title: " + driver.getTitle());

		// Select the Elements card
		WebElement element = driver.findElement(By.xpath("//div[@class='category-cards']//div[1]"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
		visibleElement.click();

		// ELEMENT - TEXTBOX
		System.out.println("Element TextBox Selected");
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "DEMOQA", "The title is verified");

		driver.findElement(By.id("item-0")).click();
		WebElement name = driver.findElement(By.tagName("input"));
		name.sendKeys("Shaik");
		WebElement email = driver.findElement(By.xpath("//input[@placeholder='name@example.com']"));
		email.sendKeys("Shaikka1@gmail.com");

		// Submit Button
		WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit' and @type ='button']"));
		WebDriverWait subWait=new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement submit=subWait.until(ExpectedConditions.elementToBeClickable(submitButton));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", submitButton);

		try {
		    submit.click();
		} catch (Exception e) {
		    // If the regular click fails, fallback to JavaScript click
		    js.executeScript("arguments[0].click();", submitButton);
		}
		

		// Validation
		String data = driver.findElement(By.xpath("//p[@id='name']")).getText();
		System.out.println(data);
		Assert.assertEquals(data, "Name:Shaik", "No data");

		// ELEMENT - CHECKBOX
		System.out.println("Element CheckBox Selected");
		driver.findElement(By.id("item-1")).click();

		// Click to toggle
		driver.findElement(By.xpath("//button[@title='Toggle']")).click();

		// Click to select checkbox
		driver.findElement(By.xpath("//span[@class='rct-checkbox']")).click();

		// Validate checkbox selection
		String checkboxClass = driver.findElement(By.xpath("//span[@class='rct-checkbox']")).getAttribute("class");
		if (checkboxClass.contains("rct-icon-check")) {
			System.out.println("Checkbox is selected");
		} else {
			System.out.println("Checkbox is not selected");
		}

		// Validate specific checkbox
		WebElement checkDownloadBtn = driver.findElement(By.xpath(
				"//*[@id='tree-node']/ol/li/ol/li[3]/span/label/span[1]//*[local-name()='svg'and (@class='rct-icon rct-icon-check' or @class='rct-icon rct-icon-uncheck')]"));
		String checkboxClass1 = checkDownloadBtn.getAttribute("class");
		System.out.println(checkboxClass1);
		if (checkboxClass1.contains("rct-icon-check")) {
			System.out.println("Checkbox is selected");
		} else {
			System.out.println("Checkbox is not selected");
		}

//		// ELEMENT - RADIO BUTTON
//		WebElement radioBtn = driver.findElement(By.id("item-2"));
//		radioBtn.click();
//		WebElement radioFrame=driver.findElement(By.xpath("//"))
//		driver.switchTo().frame();
//		WebElement radioYes = driver.findElement(By.xpath("//div[@class='custom-control custom-radio custom-control-inline']//input[@id='yesRadio']"));
//		radioYes.click();
//
//		//		WebDriverWait radioWait = new WebDriverWait(driver, Duration.ofSeconds(15));
//		WebElement yes = radioWait.until(ExpectedConditions.elementToBeClickable(radioYes));
//		yes.click();
//
//		boolean radioSelection = radioYes.isSelected();
//		if (radioSelection) {
//			System.out.println("Radio Button is selected");
//		} else {
//			System.out.println("Radio Button is not selected");
//		}
	}

	@Test(enabled = false)
	public void dynamicTable() {
		// Navigate to the web tables page
		driver.get("https://demoqa.com/webtables");

		// Maps and lists for salaries and usernames
		Map<Integer, String> salaryUserMap = new HashMap<>();
		List<Integer> empSalary = new ArrayList<>();

		// Get all rows in the table
		List<WebElement> rows = driver.findElements(By.xpath("//div[@class='rt-tbody']/div"));

		for (int i = 1; i <= rows.size(); i++) {
			// Extract username and salary from the current row
			String username = driver.findElement(By.xpath("//div[@class='rt-tbody']/div[" + i + "]//div[1]")).getText();
			String salaryText = driver.findElement(By.xpath("//div[@class='rt-tbody']/div[" + i + "]//div[5]"))
					.getText().trim();

			// Handle empty or invalid salary fields
			if (salaryText.isEmpty()) {
				System.out.println("Salary field is empty for row " + i + ". Skipping this row.");
				continue;
			}

			try {
				// Parse salary and store the data
				int salary = Integer.parseInt(salaryText);
				salaryUserMap.put(salary, username);
				empSalary.add(salary);
			} catch (NumberFormatException e) {
				System.out.println("Invalid salary format for row " + i);
				continue;
			}
		}

		// Find second highest salary
		List<Integer> uniqueSalaries = new ArrayList<>(new HashSet<>(empSalary)); // Remove duplicates
		Collections.sort(uniqueSalaries, Collections.reverseOrder()); // Sort in descending order

		if (uniqueSalaries.size() >= 2) {
			int secondHighestSalary = uniqueSalaries.get(1);
			String userWithSecondHighestSalary = salaryUserMap.get(secondHighestSalary);

			System.out.println("Second highest salary: " + secondHighestSalary);
			System.out.println("User with second highest salary: " + userWithSecondHighestSalary);
		} else {
			System.out.println("Not enough unique salaries to determine the second highest.");
		}

		// Iterate through the rows to find a specific user (e.g., "Cierra")
		for (int i = 1; i <= rows.size(); i++) {
			String username = driver.findElement(By.xpath("//div[@class='rt-tbody']/div[" + i + "]//div[1]")).getText()
					.trim();

			if (username.contains("Cierra")) {
				System.out.println("Username 'Cierra' found in row " + i);
			}
		}
	}

//	@AfterMethod
//	public void tearDown() {
//		if (driver != null) {
//			driver.quit(); // Close the browser
//		}
//	}
}
